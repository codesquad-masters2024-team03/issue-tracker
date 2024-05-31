package com.codesquad.team3.issuetracker.domain.issue.dto.request;

import com.codesquad.team3.issuetracker.domain.file.entity.UploadFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateIssue {

    @NotBlank(message ="제목은 필수입니다")
    @Size(min = 1, max = 60)
    private String title;

    @NotBlank
    @Size(min = 1, max = 500)
    private String contents;

    @NotNull
    private Integer writer;

    private List<Integer> labels;

    private final UploadFile file;

    private final List<Integer> assignee;
    private final Integer milestone;
}
