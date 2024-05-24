package com.codesquad.team3.issuetracker.domain.jwt.service;

import com.codesquad.team3.issuetracker.domain.jwt.repository.RefreshTokenRepository;
import com.codesquad.team3.issuetracker.domain.jwt.util.JwtTokenProvider;
import com.codesquad.team3.issuetracker.domain.jwt.util.JwtTokenValidator;
import com.codesquad.team3.issuetracker.domain.member.dto.response.LoginResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenValidator jwtTokenValidator;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String BEARER_PREFIX = "Bearer ";

    // 엑세스 토큰 유효성 검증
    @Override
    public String validateAccessToken(String token) {
        return validateToken(token, false);
    }

    // 리프레쉬 토큰 유효성 검증
    @Override
    public String validateRefreshToken(String token) {
        return validateToken(token, true);
    }

    // 토큰 유효성 검증
    private String validateToken(String token, boolean isRefreshToken) {
        return getTokenFromBearerString(token)
            .map(t -> {
                try {
                    jwtTokenValidator.validateToken(t);
                    if (isRefreshToken) {
                        return refreshTokenRepository.findByToken(t)
                            .map(refreshToken -> createTokens(refreshToken.getMemberId()).getAccessToken())
                            .orElse("리프레시 토큰이 유효하지 않습니다.");
                    }
                    return "토큰이 유효합니다.";
                } catch (IllegalStateException e) {
                    return e.getMessage();
                }
            }).orElse("토큰이 없습니다.");
    }

    private LoginResponse createTokens(String memberId) {
        String accessToken = jwtTokenProvider.createAccessToken(memberId);
        String refreshToken = jwtTokenProvider.createRefreshToken(memberId);
        return new LoginResponse(accessToken, refreshToken);
    }

    // 헤더에서 토큰 추출
    private Optional<String> getTokenFromBearerString(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return Optional.of(token.substring(BEARER_PREFIX.length()));
        }
        return Optional.empty();
    }

}
