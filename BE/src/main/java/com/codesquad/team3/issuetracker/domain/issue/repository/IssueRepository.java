package com.codesquad.team3.issuetracker.domain.issue.repository;

import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.support.repository.OpenCloseCrudRepository;
import com.codesquad.team3.issuetracker.support.repository.SoftDeleteCrudRepository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends SoftDeleteCrudRepository<Issue, Integer>, OpenCloseCrudRepository<Issue, Integer> {

    @Query("select * from Issue where milestone_id =:milestoneId")
    List<Issue> getIssueListByMilestoneId(@Param("milestoneId") Integer milestoneId);

}
