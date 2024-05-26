package com.codesquad.team3.issuetracker.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public String accessTokenSecretKey() {
        return "my_access_token_secret_key";
    }

    @Bean
    public String refreshTokenSecretKey() {
        return "my_refresh_token_secret_key";
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
