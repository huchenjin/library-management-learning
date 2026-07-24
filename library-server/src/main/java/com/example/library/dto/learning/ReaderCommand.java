package com.example.library.dto.learning;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReaderCommand(@NotBlank String readerNo, @NotBlank String name, String phone,
                            @Email String email, @NotNull @Min(1) Integer maxBorrowCount,
                            @NotNull Integer status) {
}
