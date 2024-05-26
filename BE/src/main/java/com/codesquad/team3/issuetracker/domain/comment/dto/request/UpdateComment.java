package com.codesquad.team3.issuetracker.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdateComment {

    @NotBlank
    private final String contents;

    @NotNull
    private final Integer commentId;

}
