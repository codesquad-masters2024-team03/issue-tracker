package com.codesquad.team3.issuetracker.domain.comment.repository;

import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;
import com.codesquad.team3.issuetracker.support.repository.SimpleCrudRepository;
import com.codesquad.team3.issuetracker.support.repository.SoftDeleteCrudRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends SimpleCrudRepository<Comment, Integer>, SoftDeleteCrudRepository<Comment, Integer> {

    @Query("select * from comment where issue_id =:issueId AND is_deleted=:isDeleted")
    List<Comment> findCommentsByIssueId(@Param("issueId") Integer issueId, boolean isDeleted);


    @Override
   default Class<Comment> getType(){
        return Comment.class;
    }
}
