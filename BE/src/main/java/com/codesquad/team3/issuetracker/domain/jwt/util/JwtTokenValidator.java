package com.codesquad.team3.issuetracker.domain.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

    private final Key secretKey;

    // 토큰 유효성 검사
    public void validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
            validateExpire(claims);
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalStateException("유효하지 않은 토큰");
        }
    }

    // 토큰 유효시간 검사
    private void validateExpire(Claims claims) {
        Date expiration = claims.getExpiration();
        if (expiration.before(new Date(System.currentTimeMillis()))) {
            throw new IllegalStateException("토큰 만료");
        }
    }
}