package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.LearningTaskNotImplementedException;
import com.example.library.common.PageResponse;
import com.example.library.dto.book.BookListItem;
import com.example.library.dto.book.BookQuery;
import com.example.library.dto.learning.BookCommand;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "图书管理")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "分页查询图书（完整示例）")
    @GetMapping
    public ApiResponse<PageResponse<BookListItem>> page(@Valid @ModelAttribute BookQuery query) {
        return ApiResponse.success(bookService.page(query));
    }

    @Operation(summary = "查询图书详情（完整示例）")
    @GetMapping("/{id}")
    public ApiResponse<BookListItem> detail(@PathVariable Long id) {
        return ApiResponse.success(bookService.detail(id));
    }

    @Operation(summary = "新增图书（LEARNING-2）")
    @PostMapping
    public ApiResponse<Void> create(@Valid @RequestBody BookCommand command) {
        // TODO(LEARNING-2): 校验 ISBN、分类和库存后新增图书。
        throw new LearningTaskNotImplementedException("LEARNING-2");
    }

    @Operation(summary = "修改图书（LEARNING-2）")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @Valid @RequestBody BookCommand command) {
        throw new LearningTaskNotImplementedException("LEARNING-2");
    }

    @Operation(summary = "删除图书（LEARNING-2）")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        throw new LearningTaskNotImplementedException("LEARNING-2");
    }
}
