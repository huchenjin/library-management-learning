package com.example.library.dto.learning;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryCommand(@NotBlank String name, @NotNull Integer sortNo, @NotNull Integer status) {
}
