package com.codesquad.team3.issuetracker.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public String accessTokenSecretKey() {
        return "mySecureAccessTokenSecretKeyForJWTGeneration123456";
    }

    @Bean
    public String refreshTokenSecretKey() {
        return "mySecureRefreshTokenSecretKeyForJWTGeneration123456";
    }

    @Bean
    public Long accessTokenExpiration() {
        return 30 * 60 * 1000L; // 30분
    }

    @Bean
    public Long refreshTokenExpiration() {
        return 30 * 24 * 60 * 60 * 1000L; // 30일
    }
}
