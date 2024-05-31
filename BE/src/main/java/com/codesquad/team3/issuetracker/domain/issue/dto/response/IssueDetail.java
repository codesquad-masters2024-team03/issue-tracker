package com.codesquad.team3.issuetracker.domain.issue.dto.response;

import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IssueDetail {

    private Integer id;
    private Integer writer;
    private String title;
    private LocalDateTime createTime;

    public static IssueDetail toEntity(Issue issue) {
        return new IssueDetail(issue.getId(),
                issue.getWriter(),
                issue.getTitle(),
                issue.getCreateTime());

    }
}
