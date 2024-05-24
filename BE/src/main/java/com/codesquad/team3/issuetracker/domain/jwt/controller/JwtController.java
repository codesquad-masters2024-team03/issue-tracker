package com.codesquad.team3.issuetracker.domain.jwt.controller;

import com.codesquad.team3.issuetracker.domain.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {

    private final JwtService jwtService;

    // 엑세스 토큰 검증
    @GetMapping("/access/validate")
    public ResponseEntity<String> validateAccess(@RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.ok(jwtService.validateAccessToken(accessToken));
    }

    // 리프레쉬 토큰 검증
    @GetMapping("/refresh/validate")
    public ResponseEntity<String> validateRefresh(@RequestHeader(value = "Authorization") String refreshToken) {
        return ResponseEntity.ok("jwt :" + jwtService.validateRefreshToken(refreshToken));
    }

}
