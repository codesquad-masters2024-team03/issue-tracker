package com.codesquad.team3.issuetracker.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public record UpdateMember(@NotBlank @Length(min=6,max=12) String password,
                           @NotBlank @Length(min=3,max=20) String nickname,
                           String imageUrl,
                           @NotBlank @NotNull LocalDate birthday,
                           @NotBlank @Length(max=50) @Email String email) {
}