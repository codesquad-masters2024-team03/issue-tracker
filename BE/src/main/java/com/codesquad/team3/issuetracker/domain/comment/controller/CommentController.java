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

    @PutMapping
    public void updateById(@RequestBody @Valid UpdateComment form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }

        commentService.update(form);
    }

    @DeleteMapping
    public void delete(@RequestBody Integer id){
        commentService.delete(id);
    }


}
