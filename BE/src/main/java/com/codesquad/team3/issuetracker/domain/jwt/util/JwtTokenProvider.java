package com.codesquad.team3.issuetracker.domain.jwt.util;

import com.codesquad.team3.issuetracker.domain.jwt.entity.RefreshToken;
import com.codesquad.team3.issuetracker.domain.jwt.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Key secretKey;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final RefreshTokenRepository refreshTokenRepository;

    // 엑세스 토큰 생성
    public String createAccessToken(String memberId) {
        return buildToken(memberId, accessTokenExpiration);
    }

    // 리프레쉬 토큰 생성 및 db 저장
    public String createRefreshToken(String memberId) {
        String refreshToken = buildToken(memberId, refreshTokenExpiration);
        Date expirationDate = new Date(System.currentTimeMillis() + refreshTokenExpiration);
        RefreshToken tokenEntity = new RefreshToken(memberId, refreshToken, expirationDate);
        refreshTokenRepository.insert(tokenEntity);
        return refreshToken;
    }

    private String buildToken(String memberId, Long expiration) {
        return Jwts.builder()
            .claim("loginId", memberId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey)
            .compact();
    }
}
