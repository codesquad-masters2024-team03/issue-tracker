package com.codesquad.team3.issuetracker.domain.comment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentDetail {

    private final Integer id;
    private final Integer writer;
    private final String contents;
    private final LocalDateTime createTime;
    private final boolean isPrimary;
}
