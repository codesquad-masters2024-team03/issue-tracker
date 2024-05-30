package com.codesquad.team3.issuetracker.oauth.controller;

import com.codesquad.team3.issuetracker.domain.member.dto.response.TokenResponse;
import com.codesquad.team3.issuetracker.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/login/oauth2/code/{provider}")
    public ResponseEntity<TokenResponse> login(@PathVariable String provider, @RequestParam String code) {
        TokenResponse tokenResponse = oAuthService.login(provider, code);
        return ResponseEntity.ok().body(tokenResponse);
    }
}