package com.example.library.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.common.BusinessException;
import com.example.library.common.ErrorCode;
import com.example.library.common.PageResponse;
import com.example.library.dto.category.CategoryListItem;
import com.example.library.dto.category.CategoryOption;
import com.example.library.dto.category.CategoryQuery;
import com.example.library.dto.learning.CategoryCommand;
import com.example.library.entity.BookCategory;
import com.example.library.mapper.BookCategoryMapper;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
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

    /**
     * 新增分类的核心业务逻辑。
     *
     * <p>Controller 负责接收和校验请求，Service 负责名称规范化、业务唯一性校验和写库。</p>
     */
    public void create(CategoryCommand command) {
        String normalizedName = command.name().strip();

        boolean nameExists = categoryMapper.exists(new LambdaQueryWrapper<BookCategory>()
                .eq(BookCategory::getName, normalizedName));
        if (nameExists) {
            throw new BusinessException(ErrorCode.CONFLICT, "分类名称已存在");
        }

        BookCategory category = new BookCategory();
        category.setName(normalizedName);
        category.setSortNo(command.sortNo());
        category.setStatus(command.status());

        try {
            categoryMapper.insert(category);
        } catch (DuplicateKeyException exception) {
            // 数据库唯一索引是最后一道防线，避免并发请求同时通过上面的 exists 校验。
            throw new BusinessException(ErrorCode.CONFLICT, "分类名称已存在");
        }
    }
}
