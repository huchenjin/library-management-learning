package com.example.library.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.library.dto.category.CategoryOption;
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
}
