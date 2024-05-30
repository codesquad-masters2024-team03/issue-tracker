package com.codesquad.team3.issuetracker.oauth.service;

import com.codesquad.team3.issuetracker.domain.member.dto.response.TokenResponse;

public interface OAuthService {

    TokenResponse login(String providerName, String code);

}
