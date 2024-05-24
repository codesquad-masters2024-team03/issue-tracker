package com.codesquad.team3.issuetracker.domain.jwt.service;

public interface JwtService {
    String validateAccessToken(String accessToken);

    String validateRefreshToken(String refreshToken);

}
