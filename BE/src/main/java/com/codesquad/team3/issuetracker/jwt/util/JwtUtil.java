package com.codesquad.team3.issuetracker.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final String accessTokenSecretKey;
    private final String refreshTokenSecretKey;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;

    public String createAccessToken(String memberId) {
        return buildToken(memberId, accessTokenExpiration, accessTokenSecretKey);
    }

    public String createRefreshToken(String memberId) {
        return buildToken(memberId, refreshTokenExpiration, refreshTokenSecretKey);
    }

    public Boolean validateAccessToken(String token, String loginId) {
        return validateToken(token, loginId, accessTokenSecretKey);
    }

    public Boolean validateRefreshToken(String token, String loginId) {
        return validateToken(token, loginId, refreshTokenSecretKey);
    }

    public String extractLoginId(String token) {
        return extractClaim(token, Claims::getSubject, accessTokenSecretKey);
    }

    private Boolean validateToken(String token, String loginId, String secretKey) {
        final String extractedLoginId = extractClaim(token, Claims::getSubject, secretKey);
        return (extractedLoginId.equals(loginId) && !isTokenExpired(token, secretKey));
    }

    private String buildToken(String memberId, Long expiration, String secretKey) {
        return Jwts.builder()
            .setSubject(memberId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    private Boolean isTokenExpired(String token, String secretKey) {
        return extractClaim(token, Claims::getExpiration, secretKey).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secretKey) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String secretKey) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }
}
