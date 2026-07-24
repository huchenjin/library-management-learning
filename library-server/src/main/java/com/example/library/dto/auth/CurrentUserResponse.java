package com.example.library.dto.auth;

public record CurrentUserResponse(Long id, String username, String displayName, String role) {
}
