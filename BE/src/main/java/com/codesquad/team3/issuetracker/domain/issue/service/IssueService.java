package com.codesquad.team3.issuetracker.domain.issue.service;

import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.global.exceptions.NoSuchRecordException;
import com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags;

import java.util.List;

public interface IssueService {

    void createIssue(CreateIssue createIssue);

    void close(Integer id);

    void open(Integer id);

    List<Issue> getAllIssues(int loadCount);

    int getIssueCount(OpenCloseSearchFlags flags);

    void openIssue(List<Integer> issueIds) throws NoSuchRecordException;

    void closeIssue(List<Integer> issueIds) throws NoSuchRecordException;

    List<Issue> getIssueByMilestoneId(Integer milestoneId);
}
