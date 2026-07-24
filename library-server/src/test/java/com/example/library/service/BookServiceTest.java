package com.example.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.common.BusinessException;
import com.example.library.dto.book.BookListItem;
import com.example.library.dto.book.BookQuery;
import com.example.library.mapper.BookMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class BookServiceTest {
    private final BookMapper mapper = mock(BookMapper.class);
    private final BookService service = new BookService(mapper);

    @Test
    void shouldReturnPagedBooks() {
        BookListItem book = new BookListItem();
        book.setId(1L);
        book.setTitle("Java 核心技术");
        Page<BookListItem> result = new Page<>(1, 10);
        result.setRecords(List.of(book));
        result.setTotal(1);
        when(mapper.selectBookPage(any(), any())).thenReturn(result);

        var response = service.page(new BookQuery());

        assertEquals(1, response.total());
        assertEquals("Java 核心技术", response.records().getFirst().getTitle());
    }

    @Test
    void shouldReturnNotFoundForMissingBook() {
        when(mapper.selectBookDetail(999L)).thenReturn(null);
        assertThrows(BusinessException.class, () -> service.detail(999L));
    }
}
