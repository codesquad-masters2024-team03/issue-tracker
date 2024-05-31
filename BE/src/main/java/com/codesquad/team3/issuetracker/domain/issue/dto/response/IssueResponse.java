package com.codesquad.team3.issuetracker.domain.issue.dto.response;

import com.codesquad.team3.issuetracker.domain.comment.dto.response.CommentDetail;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.domain.labels.dto.response.LabelDetail;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberDetail;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneDetail;
import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class IssueResponse {

    private final IssueDetail issue;
    private final List<CommentDetail> comments;
    private final List<MemberDetail> assignee;
    private final List<LabelDetail> labels;
    private final List<MilestoneDetail> milestone;


    public static IssueResponse toEntity(Issue issue, List<CommentDetail> comments, List<MemberDetail> assignees, List<LabelDetail> label, Milestone milestone) {
        return new IssueResponse(IssueDetail.toEntity(issue), comments, assignees, label, MilestoneDetail.toEntity(milestone));

    }

    public String getContents(){
        return comments.get(0).getContents();
    }
}
