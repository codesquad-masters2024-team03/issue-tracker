package com.codesquad.team3.issuetracker.domain.comment.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentDetail {

    private Integer id;
    private Integer writer;
    private String contents;
    private Integer issueId;
    private LocalDateTime createTime;
    private boolean isPrimary;
}
