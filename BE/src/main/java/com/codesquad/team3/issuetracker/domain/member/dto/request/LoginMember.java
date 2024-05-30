package com.codesquad.team3.issuetracker.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginMember(@NotBlank String memberId, @NotBlank String password) {

}