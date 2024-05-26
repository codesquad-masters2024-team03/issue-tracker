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

    @NotBlank(message = "이슈 제목은 필수입니다")
    @Size(min = 1, max = 60, message = "제목은 1 ~ 60 글자여야합니다")
    private String title;

    @NotBlank(message = "이슈 내용은 필수 입니다")
    @Size(min = 1, max = 500, message = "이슈 내용은 1 ~ 500글자여야 합니다")
    private String contents;

    @NotNull(message = "작성자는 필수입니다")
    private Integer writer;

    private List<Integer> labels;

    private final UploadFile file;

    private final List<Integer> assignee;

    private final Integer milestone;
}
