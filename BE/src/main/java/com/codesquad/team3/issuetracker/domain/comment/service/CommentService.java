package com.codesquad.team3.issuetracker.domain.comment.service;

import com.codesquad.team3.issuetracker.domain.comment.dto.request.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.request.UpdateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.response.CommentDetail;

import java.util.List;

public interface CommentService {

    void create(Integer id, CreateComment form, boolean isPrimary);

    void softDelete(Integer id);

    void restore(Integer id);

    void update(Integer id, UpdateComment form);

    void updatePrimary(Integer id, String newContent);

    public List<CommentDetail> findComments(Integer id);
}
