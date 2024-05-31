package com.codesquad.team3.issuetracker.domain.comment.controller;

import com.codesquad.team3.issuetracker.domain.comment.dto.request.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.request.UpdateComment;
import com.codesquad.team3.issuetracker.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}")
    public void create(@PathVariable("id")Integer id, @RequestBody @Valid CreateComment form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){


        }
        commentService.create(id, form, false);
    }

    @PutMapping("/{id}")
    public void updateById(@PathVariable("id") Integer id, @RequestBody @Valid UpdateComment form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }

        commentService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        commentService.softDelete(id);
    }

    @PutMapping("/restore/{id}")
    public void restore(@PathVariable("id") Integer id){
        commentService.restore(id);
    }


}
