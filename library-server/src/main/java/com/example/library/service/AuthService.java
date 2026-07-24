package com.example.library.service;

import com.example.library.common.BusinessException;
import com.example.library.common.ErrorCode;
import com.example.library.dto.auth.CurrentUserResponse;
import com.example.library.dto.auth.LoginRequest;
import com.example.library.dto.auth.LoginResponse;
import com.example.library.entity.SysUser;
import com.example.library.mapper.SysUserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final SysUserMapper userMapper;
    private final JwtTokenService tokenService;

    public AuthService(AuthenticationManager authenticationManager, SysUserMapper userMapper,
                       JwtTokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password()));
        } catch (AuthenticationException exception) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }
        SysUser user = userMapper.selectByUsername(request.username());
        return new LoginResponse(tokenService.createToken(user), "Bearer", tokenService.expiresInSeconds());
    }

    public CurrentUserResponse currentUser(String username) {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        return new CurrentUserResponse(user.getId(), user.getUsername(), user.getDisplayName(), user.getRole());
    }
}
