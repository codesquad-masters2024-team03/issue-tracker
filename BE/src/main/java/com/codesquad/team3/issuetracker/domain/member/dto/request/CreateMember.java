package com.codesquad.team3.issuetracker.domain.member.dto.request;

import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Length;

public record CreateMember(@NotBlank @Length(min=6,max=16) String memberId,
                           @NotBlank @Length(min=6,max=12) String password,
                           @NotBlank @Length(min=3,max=20) String nickname,
                           String imageUrl,
                           @JsonFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate birthday,
                           @NotBlank @Length(max=50) @Email String email) {

    public Member toMember() {
        return Member.builder()
            .memberId(memberId)
            .password(password)
            .nickname(nickname)
            .imageUrl(imageUrl)
            .birthday(birthday)
            .joinMethod("GENERAL")
            .joinTime(LocalDateTime.now())
            .email(email)
            .build();
    }
}