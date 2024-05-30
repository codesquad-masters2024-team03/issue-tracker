package com.codesquad.team3.issuetracker.domain.issue.repository;

import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags;
import com.codesquad.team3.issuetracker.support.repository.OpenCloseCrudRepository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends OpenCloseCrudRepository<Issue, Integer> {

    @Query("select * from issue where milestone_id =:milestoneId")
    List<Issue> getIssuesByMilestoneId(@Param("milestoneId") Integer milestoneId);

    @Query("select * from issue where is_closed = 0")
    List<Issue> findAllOpenIssues();

    @Query("select * from issue where is_closed = 1")
    List<Issue> findAllCloseIssues();


    @Override
    default Class<Issue> getType() {

        return Issue.class;
    }

}
