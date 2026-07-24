package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.LearningTaskNotImplementedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "统计看板")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Operation(summary = "统计摘要（LEARNING-6）")
    @GetMapping("/summary")
    public ApiResponse<Void> summary() {
        // TODO(LEARNING-6): 使用自定义 SQL 返回馆藏、读者、借出和逾期统计。
        throw new LearningTaskNotImplementedException("LEARNING-6");
    }
}
