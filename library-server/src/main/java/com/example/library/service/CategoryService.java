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
import com.example.library.entity.Book;
import com.example.library.mapper.BookMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final BookCategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    public CategoryService(
            BookCategoryMapper categoryMapper,
            BookMapper bookMapper) {
        this.categoryMapper = categoryMapper;
        this.bookMapper = bookMapper;
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

    public void update(Long id, CategoryCommand command) {
        // 1. 查询目标分类，同时会自动排除已逻辑删除的数据
        BookCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "分类不存在");
        }

        // 2. 统一处理名称，避免“文学”和“ 文学 ”被当成不同输入
        String normalizedName = command.name().strip();

        // 3. 检查其他分类是否使用了这个名称
        boolean nameExists = categoryMapper.exists(
                new LambdaQueryWrapper<BookCategory>()
                        .eq(BookCategory::getName, normalizedName)
                        .ne(BookCategory::getId, id)
        );

        if (nameExists) {
            throw new BusinessException(ErrorCode.CONFLICT, "分类名称已存在");
        }

        // 4. 更新允许修改的字段
        category.setName(normalizedName);
        category.setSortNo(command.sortNo());
        category.setStatus(command.status());

        try {
            // 5. 根据实体中的 ID 更新
            int affectedRows = categoryMapper.updateById(category);

            if (affectedRows != 1) {
                throw new BusinessException(ErrorCode.NOT_FOUND, "分类不存在");
            }
        } catch (DuplicateKeyException exception) {
            // exists 负责友好提示，数据库唯一索引负责处理并发竞争
            throw new BusinessException(ErrorCode.CONFLICT, "分类名称已存在");
        }
    }

    @Transactional
    public void delete(Long id) {
        // 1. 判断分类是否存在
        BookCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "分类不存在"
            );
        }

        // 2. 判断是否被未删除的图书引用
        boolean referenced = bookMapper.exists(
                new LambdaQueryWrapper<Book>()
                        .eq(Book::getCategoryId, id)
        );

        if (referenced) {
            throw new BusinessException(
                    ErrorCode.CONFLICT,
                    "该分类已被图书引用，不能删除"
            );
        }

        // 3. 执行逻辑删除
        int affectedRows = categoryMapper.deleteById(id);

        if (affectedRows != 1) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "分类不存在"
            );
        }
    }
}
