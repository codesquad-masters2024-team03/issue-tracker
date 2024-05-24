package com.codesquad.team3.issuetracker.domain.jwt.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public Key secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    public Long accessTokenExpiration() {
        return 10 * 60 * 1000L; // 10분
    }

    @Bean
    public Long refreshTokenExpiration() {
        return 30 * 24 * 60 * 60 * 1000L; // 30일
    }
}
