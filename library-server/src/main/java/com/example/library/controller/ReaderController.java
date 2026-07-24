package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.LearningTaskNotImplementedException;
import com.example.library.dto.learning.ReaderCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "读者管理")
@RestController
@RequestMapping("/api/readers")
public class ReaderController {
    @Operation(summary = "读者分页（LEARNING-3）")
    @GetMapping
    public ApiResponse<Void> page() { throw task(); }

    @Operation(summary = "新增读者（LEARNING-3）")
    @PostMapping
    public ApiResponse<Void> create(@Valid @RequestBody ReaderCommand command) { throw task(); }

    @Operation(summary = "修改读者（LEARNING-3）")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @Valid @RequestBody ReaderCommand command) { throw task(); }

    @Operation(summary = "删除读者（LEARNING-3）")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { throw task(); }

    private LearningTaskNotImplementedException task() {
        // TODO(LEARNING-3): 完成读者 CRUD、唯一性和未归还记录校验。
        return new LearningTaskNotImplementedException("LEARNING-3");
    }
}
