package com.codesquad.team3.issuetracker.domain.issue.service;

import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueInfo;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueResponse;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.global.exceptions.NoSuchRecordException;
import com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags;

import java.util.List;

public interface IssueService {

    void create(CreateIssue createIssue);


    List<IssueInfo> getOpenIssues();

    List<IssueInfo> getClosedIssues();

    int getIssueCount(OpenCloseSearchFlags flags);

    void open(List<Integer> issueIds) throws NoSuchRecordException;

    public void close(List<Integer> issueIds) throws NoSuchRecordException;

    List<Issue> getIssueByMilestoneId(Integer milestoneId);

    IssueResponse getIssue(Integer id);

    void putAssigneeLater(List<Integer> index, Integer id);

    void putLabelLater(List<Integer> index, Integer id);

    void putMilestone(Integer id, Integer milestone);

    void deleteAssignee(Integer id, Integer assignee);

    void deleteLabel(Integer id, Integer label);

    void deleteMilestone(Integer id);

    void updateTitle(Integer id, String newTitle);

    void updateContents(Integer id, String newContents);
}
