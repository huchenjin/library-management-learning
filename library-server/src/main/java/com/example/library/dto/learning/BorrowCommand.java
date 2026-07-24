package com.example.library.dto.learning;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record BorrowCommand(@NotNull Long readerId, @NotNull Long bookId, @NotNull LocalDateTime dueAt) {
}
