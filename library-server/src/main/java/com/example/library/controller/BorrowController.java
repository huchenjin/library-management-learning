package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.LearningTaskNotImplementedException;
import com.example.library.dto.learning.BorrowCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "借阅管理")
@RestController
@RequestMapping("/api/borrows")
public class BorrowController {
    @Operation(summary = "借阅记录分页（LEARNING-4）")
    @GetMapping
    public ApiResponse<Void> page() { throw new LearningTaskNotImplementedException("LEARNING-4"); }

    @Operation(summary = "借出图书（LEARNING-4）")
    @PostMapping
    public ApiResponse<Void> borrow(@Valid @RequestBody BorrowCommand command) {
        // TODO(LEARNING-4): 在事务中校验读者、借阅上限和库存并创建记录。
        throw new LearningTaskNotImplementedException("LEARNING-4");
    }

    @Operation(summary = "归还图书（LEARNING-5）")
    @PostMapping("/{id}/return")
    public ApiResponse<Void> returnBook(@PathVariable Long id) {
        // TODO(LEARNING-5): 防止重复归还，并在同一事务中恢复库存。
        throw new LearningTaskNotImplementedException("LEARNING-5");
    }
}
