package com.example.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.library.common.BusinessException;
import com.example.library.common.ErrorCode;
import com.example.library.dto.learning.CategoryCommand;
import com.example.library.entity.BookCategory;
import com.example.library.mapper.BookCategoryMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DuplicateKeyException;

class CategoryServiceTest {
    private final BookCategoryMapper mapper = mock(BookCategoryMapper.class);
    private final CategoryService service = new CategoryService(mapper);

    @Test
    void shouldCreateCategoryWithNormalizedName() {
        when(mapper.exists(any())).thenReturn(false);

        service.create(new CategoryCommand("  哲学  ", 50, 1));

        ArgumentCaptor<BookCategory> captor = ArgumentCaptor.forClass(BookCategory.class);
        verify(mapper).insert(captor.capture());
        BookCategory category = captor.getValue();
        assertEquals("哲学", category.getName());
        assertEquals(50, category.getSortNo());
        assertEquals(1, category.getStatus());
    }

    @Test
    void shouldRejectDuplicateCategoryName() {
        when(mapper.exists(any())).thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.create(new CategoryCommand("计算机", 10, 1)));

        assertEquals(ErrorCode.CONFLICT, exception.getErrorCode());
        assertEquals("分类名称已存在", exception.getMessage());
    }

    @Test
    void shouldTranslateDatabaseUniqueConstraintToConflict() {
        when(mapper.exists(any())).thenReturn(false);
        when(mapper.insert(any(BookCategory.class)))
                .thenThrow(new DuplicateKeyException("uk_book_category_name"));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.create(new CategoryCommand("哲学", 50, 1)));

        assertEquals(ErrorCode.CONFLICT, exception.getErrorCode());
        assertEquals("分类名称已存在", exception.getMessage());
    }

    @Test
    void shouldUpdateCategory() {
        BookCategory existing = new BookCategory();
        existing.setId(1L);
        existing.setName("文学");

        when(mapper.selectById(1L)).thenReturn(existing);
        when(mapper.exists(any())).thenReturn(false);
        when(mapper.updateById(any(BookCategory.class))).thenReturn(1);

        service.update(1L, new CategoryCommand("  哲学  ", 50, 1));

        assertEquals("哲学", existing.getName());
        assertEquals(50, existing.getSortNo());
        assertEquals(1, existing.getStatus());
        verify(mapper).updateById(existing);
    }

    @Test
    void shouldRejectMissingCategoryWhenUpdating() {
        when(mapper.selectById(999L)).thenReturn(null);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.update(
                        999L,
                        new CategoryCommand("哲学", 50, 1)
                )
        );

        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void shouldRejectDuplicateNameWhenUpdating() {
        BookCategory existing = new BookCategory();
        existing.setId(1L);

        when(mapper.selectById(1L)).thenReturn(existing);
        when(mapper.exists(any())).thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.update(
                        1L,
                        new CategoryCommand("计算机", 50, 1)
                )
        );

        assertEquals(ErrorCode.CONFLICT, exception.getErrorCode());
    }
}
