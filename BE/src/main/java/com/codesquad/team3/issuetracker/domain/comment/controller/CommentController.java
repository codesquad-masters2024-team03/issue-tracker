package com.codesquad.team3.issuetracker.domain.comment.controller;

import com.codesquad.team3.issuetracker.domain.comment.dto.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.entity.Comment;
import com.codesquad.team3.issuetracker.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void createComment(@RequestBody @Valid CreateComment form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

        }
        commentService.createComment(form);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        commentService.delete(id);
    }

    @PutMapping("/{id}")
    public void updateById(@PathVariable Integer id, @RequestBody @Valid CreateComment form,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }

        commentService.update(id, form);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> findAll(){
        List<Comment> commentList = commentService.findAll();

        return ResponseEntity.ok(commentList);
    }


}
