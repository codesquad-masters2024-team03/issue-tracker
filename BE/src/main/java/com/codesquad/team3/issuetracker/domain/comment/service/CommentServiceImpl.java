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
    public void delete(Integer id) {

        Comment comment = commentRepository.findById(id).orElseThrow();
        if(comment.isPrimary()){
            return;
            //예외 처리
        }
        commentRepository.deleteById(id);
    }

    @Override
    public void update(UpdateComment form) {

        Comment comment = commentRepository.findById(form.getCommentId()).orElseThrow();
        commentRepository.update(new Comment(comment.getId(), comment.getWriter(), form.getContents(), comment.getIssueId(), comment.getCreateTime()));
    }

    @Override
    public void updatePrimary(Integer id, String newContent) {
        List<Comment> commentsByIssueId = commentRepository.findCommentsByIssueId(id);
        commentRepository.update(Comment.updatedComment(commentsByIssueId.get(0), newContent));

    }

    @Override
    public List<CommentDetail> findComments(Integer id) {
        List<Comment> comments = commentRepository.findCommentsByIssueId(id);

        return comments.stream().map(i->new CommentDetail(i.getId(), i.getWriter(), i.getContents(), i.getIssueId(),  i.getCreateTime(), i.isPrimary()))
                .toList();
    }

}
