package com.codesquad.team3.issuetracker.jwt.interceptor;

import com.codesquad.team3.issuetracker.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtRequestFilter implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String jwt = authorizationHeader.substring(BEARER_PREFIX.length());
            String loginId = jwtUtil.extractLoginId(jwt);
            if (loginId != null && jwtUtil.validateAccessToken(jwt, loginId)) {
                request.setAttribute("loginId", loginId);
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
