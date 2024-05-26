package com.codesquad.team3.issuetracker.config;

import com.codesquad.team3.issuetracker.jwt.interceptor.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtRequestFilter)
            .addPathPatterns("/**")
            .excludePathPatterns("/members", "/members/login", "/members/refresh-token");
    }
}
