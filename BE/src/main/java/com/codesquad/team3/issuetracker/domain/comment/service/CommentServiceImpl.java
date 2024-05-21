package com.codesquad.team3.issuetracker.domain.comment.service;

import com.codesquad.team3.issuetracker.domain.comment.dto.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;
import com.codesquad.team3.issuetracker.domain.comment.repository.CommentRepository;
import com.codesquad.team3.issuetracker.domain.file.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Override
    public void createComment(CreateComment form) {
        commentRepository.insert(
                new Comment(form.getWriterId(),
                        form.getContents(),
                        form.getIssueId(),
                        form.getUploadFile(),
                        LocalDateTime.now()

        ));
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, CreateComment form) {
        commentRepository.update(new Comment(id, form.getWriterId(),form.getContents(),
                form.getIssueId(), new UploadFile(1, null)));
    }

    @Override
    public List<Comment> findAll() {
        return (List<Comment>) commentRepository.findAll();
    }


}
