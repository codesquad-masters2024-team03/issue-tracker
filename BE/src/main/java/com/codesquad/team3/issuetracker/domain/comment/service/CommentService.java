package com.codesquad.team3.issuetracker.domain.comment.service;

import com.codesquad.team3.issuetracker.domain.comment.dto.request.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.request.UpdateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.response.CommentDetail;

import java.util.List;

public interface CommentService {

    void create(Integer id, CreateComment form, boolean isPrimary);

    void delete(Integer id);

    void update(UpdateComment form);

    void updatePrimary(Integer id, String newContent);

    public List<CommentDetail> findComments(Integer id);
}
