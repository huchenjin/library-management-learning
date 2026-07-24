package com.example.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.library.config.JwtProperties;
import com.example.library.entity.SysUser;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

class JwtTokenServiceTest {
    @Test
    void shouldCreateTwoHourAdminToken() {
        JwtEncoder encoder = mock(JwtEncoder.class);
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("signed-token");
        when(encoder.encode(any())).thenReturn(jwt);
        JwtTokenService service = new JwtTokenService(encoder,
                new JwtProperties("a-development-secret-with-32-bytes", Duration.ofHours(2)));
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setDisplayName("管理员");
        user.setRole("ADMIN");

        assertEquals("signed-token", service.createToken(user));
        assertEquals(7200, service.expiresInSeconds());
    }
}
