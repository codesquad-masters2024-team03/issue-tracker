package com.codesquad.team3.issuetracker.domain.labels.repository;

import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
import com.codesquad.team3.issuetracker.support.repository.SimpleCrudRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends SimpleCrudRepository<Label, Integer> {

    @Query("Select * from labels_in_issue where issue_id=:issueId")
    List<Label> findByIssueId(@Param("issueId") Integer issueId);
}
