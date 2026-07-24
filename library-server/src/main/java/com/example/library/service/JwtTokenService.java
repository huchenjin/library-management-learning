package com.example.library.service;

import com.example.library.config.JwtProperties;
import com.example.library.entity.SysUser;
import java.time.Instant;
import java.util.List;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;

    public JwtTokenService(JwtEncoder jwtEncoder, JwtProperties properties) {
        this.jwtEncoder = jwtEncoder;
        this.properties = properties;
    }

    public String createToken(SysUser user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("library-server")
                .issuedAt(now)
                .expiresAt(now.plus(properties.expiresIn()))
                .subject(user.getUsername())
                .claim("uid", user.getId())
                .claim("displayName", user.getDisplayName())
                .claim("roles", List.of(user.getRole()))
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long expiresInSeconds() {
        return properties.expiresIn().toSeconds();
    }
}
