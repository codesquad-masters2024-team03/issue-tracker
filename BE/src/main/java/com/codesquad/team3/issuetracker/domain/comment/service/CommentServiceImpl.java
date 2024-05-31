package com.codesquad.team3.issuetracker.domain.comment.service;

import com.codesquad.team3.issuetracker.domain.comment.dto.request.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.request.UpdateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.response.CommentDetail;
import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;
import com.codesquad.team3.issuetracker.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void create(Integer id, CreateComment form, boolean isPrimary) {
        commentRepository.insert(
                new Comment(form.getWriterId(),
                        form.getContents(),
                        id,
                        LocalDateTime.now(),
                        isPrimary

                ));
    }


    @Override
    public void softDelete(Integer id) {

        Comment comment = commentRepository.findById(id).orElseThrow();
        if(comment.isPrimary()){
            return;
            //예외 처리
        }
        commentRepository.softDelete(comment);
    }

    @Override
    public void restore(Integer id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.recover(comment);
    }

    @Override
    public void update(Integer id, UpdateComment form) {

        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.update(form.getContents());
        commentRepository.update(comment);
    }

    @Override
    public void updatePrimary(Integer id, String newContent) {
        List<Comment> commentsByIssueId = commentRepository.findCommentsByIssueId(id, false);
        Comment primaryComment = commentsByIssueId.get(0);

        //모든 이슈마다 제일 처음에 달린 comment가 그 이슈의 본문이다.

        primaryComment.update(newContent);
        commentRepository.update(primaryComment);

    }

    @Override
    public List<CommentDetail> findComments(Integer id) {
        List<Comment> comments = commentRepository.findCommentsByIssueId(id, false);

        return comments.stream()
                .map(i->new CommentDetail(i.getId(),
                        i.getWriter(),
                        i.getContents(),
                        i.getCreateTime(),
                        i.isPrimary()))
                .toList();
    }

}
