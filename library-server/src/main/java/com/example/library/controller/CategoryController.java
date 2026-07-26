package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.LearningTaskNotImplementedException;
import com.example.library.common.PageResponse;
import com.example.library.dto.category.CategoryListItem;
import com.example.library.dto.category.CategoryOption;
import com.example.library.dto.category.CategoryQuery;
import com.example.library.dto.learning.CategoryCommand;
import com.example.library.service.BookService;
import com.example.library.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Tag(name = "分类管理")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "分类下拉选项（完整示例）")
    @GetMapping("/options")
    public ApiResponse<List<CategoryOption>> options() {
        return ApiResponse.success(categoryService.options());
    }

    @Operation(summary = "分类分页（LEARNING-1）")
    @GetMapping
    public ApiResponse<PageResponse<CategoryListItem>> page(@Valid @ModelAttribute CategoryQuery query) {
        return  ApiResponse.success(categoryService.page(query));
    }

    @Operation(summary = "新增分类（LEARNING-1）")
    @PostMapping
    public ApiResponse<Void> create(@Valid @RequestBody CategoryCommand command) { throw task(); }

    @Operation(summary = "修改分类（LEARNING-1）")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @Valid @RequestBody CategoryCommand command) { throw task(); }

    @Operation(summary = "删除分类（LEARNING-1）")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { throw task(); }

    private LearningTaskNotImplementedException task() {
        // TODO(LEARNING-1): 完成分类 CRUD、唯一性和关联校验。
        return new LearningTaskNotImplementedException("LEARNING-1");
    }
}
