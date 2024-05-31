package com.codesquad.team3.issuetracker.domain.filter.dto;

import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.Assigner;
import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.IssueLabel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchedIssue {

    private Integer id;
    private Integer writer;
    private String title;
    private LocalDateTime createTime;
    private Integer milestoneId;
    private Set<IssueLabel> labels;
    private Set<Assigner> assignees;
}
