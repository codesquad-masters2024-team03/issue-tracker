package com.codesquad.team3.issuetracker.domain.comment.dto;

import com.codesquad.team3.issuetracker.domain.file.entity.UploadFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateComment {

    @NotNull
    private final Integer writerId;
    @NotBlank
    private final String contents;
    @NotNull
    private final int issueId;
    private UploadFile uploadFile;
}
