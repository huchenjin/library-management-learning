package com.example.library.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.common.PageResponse;
import com.example.library.dto.book.BookListItem;
import com.example.library.dto.book.BookQuery;
import com.example.library.dto.category.CategoryListItem;
import com.example.library.dto.category.CategoryOption;
import com.example.library.dto.category.CategoryQuery;
import com.example.library.entity.BookCategory;
import com.example.library.mapper.BookCategoryMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final BookCategoryMapper categoryMapper;

    public CategoryService(BookCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryOption> options() {
        return categoryMapper.selectList(new LambdaQueryWrapper<BookCategory>()
                        .eq(BookCategory::getStatus, 1)
                        .orderByAsc(BookCategory::getSortNo, BookCategory::getId))
                .stream()
                .map(category -> new CategoryOption(category.getId(), category.getName()))
                .toList();
    }

    public PageResponse<CategoryListItem> page(CategoryQuery query) {
        Page<CategoryListItem> page = new Page<>(query.getPage(), query.getSize());
        return PageResponse.from(categoryMapper.selectCategoryPage(page, query));
    }
}
