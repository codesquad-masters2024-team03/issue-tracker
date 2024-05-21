package com.codesquad.team3.issuetracker.domain.issue.dto.response;

import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class IssuseResponse {

    private final Issue issue;
    private final List<Comment> comments;
}
