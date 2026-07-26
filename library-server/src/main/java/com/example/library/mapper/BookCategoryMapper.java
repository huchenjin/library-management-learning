package com.example.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.dto.category.CategoryListItem;
import com.example.library.dto.category.CategoryQuery;
import com.example.library.entity.BookCategory;
import org.apache.ibatis.annotations.Param;

public interface BookCategoryMapper extends BaseMapper<BookCategory> {
    Page<CategoryListItem> selectCategoryPage(Page<CategoryListItem> page, @Param("query") CategoryQuery query);
    CategoryListItem selectCategoryDetail(@Param("id") Long id);
}
