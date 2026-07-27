package com.example.library.dto.learning;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryCommand(
        @NotBlank(message = "分类名称不能为空")
        @Size(max = 80, message = "分类名称不能超过 80 个字符")
        String name,
        @NotNull(message = "排序号不能为空")
        Integer sortNo,
        @NotNull(message = "状态不能为空")
        @Min(value = 0, message = "状态只能是 0 或 1")
        @Max(value = 1, message = "状态只能是 0 或 1")
        Integer status) {
}
