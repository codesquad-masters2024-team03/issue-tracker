package com.codesquad.team3.issuetracker.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;

@Configuration
public class TestConfig {

    @Bean
    public JdbcMappingContext jdbcMappingContext() {
        return new JdbcMappingContext();
    }
}
