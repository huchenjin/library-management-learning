package com.example.library.dto.learning;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record BookCommand(@NotBlank String isbn, @NotBlank String title, @NotBlank String author,
                          String publisher, LocalDate publishDate, @NotNull Long categoryId,
                          @NotNull @Min(0) Integer totalStock, String location, @NotNull Integer status) {
}
