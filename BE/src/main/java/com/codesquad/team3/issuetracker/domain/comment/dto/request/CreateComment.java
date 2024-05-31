package com.codesquad.team3.issuetracker.domain.comment.dto.request;

import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateComment {

    @NotNull
    private final Integer writerId;
    @NotBlank
    private final String contents;



    public static CreateComment toEntity(CreateIssue createIssue, Issue issue) {
       return new CreateComment(createIssue.getWriter(),
                createIssue.getContents());
    }
}
