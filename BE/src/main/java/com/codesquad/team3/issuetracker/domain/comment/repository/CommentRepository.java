package com.codesquad.team3.issuetracker.domain.comment.repository;

import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;
import com.codesquad.team3.issuetracker.support.repository.SimpleCrudRepository;

public interface CommentRepository extends SimpleCrudRepository<Comment, Integer> {
}
