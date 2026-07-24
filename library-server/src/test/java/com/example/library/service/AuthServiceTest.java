package com.example.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.library.common.BusinessException;
import com.example.library.dto.auth.LoginRequest;
import com.example.library.entity.SysUser;
import com.example.library.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

class AuthServiceTest {
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final SysUserMapper userMapper = mock(SysUserMapper.class);
    private final JwtTokenService tokenService = mock(JwtTokenService.class);
    private final AuthService service = new AuthService(authenticationManager, userMapper, tokenService);

    @Test
    void shouldReturnTokenAfterAuthentication() {
        SysUser user = new SysUser();
        user.setUsername("admin");
        when(userMapper.selectByUsername("admin")).thenReturn(user);
        when(tokenService.createToken(user)).thenReturn("token");
        when(tokenService.expiresInSeconds()).thenReturn(7200L);

        var result = service.login(new LoginRequest("admin", "admin123"));

        assertEquals("token", result.accessToken());
        assertEquals(7200L, result.expiresIn());
    }

    @Test
    void shouldRejectInvalidCredentials() {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad credentials"));
        assertThrows(BusinessException.class,
                () -> service.login(new LoginRequest("admin", "wrong")));
    }
}
