package com.codesquad.team3.issuetracker.domain.comment.repository;

import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;
import com.codesquad.team3.issuetracker.support.repository.SimpleCrudRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends SimpleCrudRepository<Comment, Integer> {

    @Query("select * from comment where issue_id =:issueId")
    List<Comment> findCommentsByIssueId(@Param("issueId") Integer issueId);

}
