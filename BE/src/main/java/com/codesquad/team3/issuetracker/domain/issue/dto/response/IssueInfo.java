package com.codesquad.team3.issuetracker.domain.issue.dto.response;

import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.domain.labels.dto.response.LabelDetail;
import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class IssueInfo {

    private final Integer id;
    private final String title;
    private final Integer writer;
    private final LocalDateTime createTime;
    private final List<LabelDetail> lables;
    private final String milestoneTitle;

    public static IssueInfo toEntity(Issue issue, List<LabelDetail> label, Milestone milestone) {

        if(milestone==null){
            return new IssueInfo(issue.getId(), issue.getTitle(), issue.getWriter(), issue.getCreateTime(), label, null);
        }
        return new IssueInfo(issue.getId(), issue.getTitle(), issue.getWriter(), issue.getCreateTime(), label, milestone.getTitle());
    }
}
