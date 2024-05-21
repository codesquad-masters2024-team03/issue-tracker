package com.codesquad.team3.issuetracker.domain.comment.service;

import com.codesquad.team3.issuetracker.domain.comment.dto.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    void createComment(CreateComment form);

    void delete(Integer id);

    void update(Integer id, CreateComment form);

    List<Comment> findAll();
}
