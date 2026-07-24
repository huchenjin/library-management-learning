package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.dto.auth.CurrentUserResponse;
import com.example.library.dto.auth.LoginRequest;
import com.example.library.dto.auth.LoginResponse;
import com.example.library.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "认证")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @Operation(summary = "获取当前管理员")
    @GetMapping("/me")
    public ApiResponse<CurrentUserResponse> me(@AuthenticationPrincipal Jwt jwt) {
        return ApiResponse.success(authService.currentUser(jwt.getSubject()));
    }
}
