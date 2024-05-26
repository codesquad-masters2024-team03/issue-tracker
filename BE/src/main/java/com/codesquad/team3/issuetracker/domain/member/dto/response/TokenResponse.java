package com.codesquad.team3.issuetracker.domain.member.dto.response;

import lombok.Getter;

@Getter
public record TokenResponse(String accessToken, String refreshToken) {

}