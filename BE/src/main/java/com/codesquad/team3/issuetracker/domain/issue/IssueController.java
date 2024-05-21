package com.codesquad.team3.issuetracker.domain.issue;

import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping("")
    public void createIssue(@RequestBody @Valid CreateIssue createIssue, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

        }
        issueService.createIssue(createIssue);
    }

    @PutMapping("/close/{id}")
    public void close(@PathVariable("id") Integer id){
        issueService.close(id);
    }

    @PutMapping("/open/{id}")
    public void open(@PathVariable("id") Integer id){
        issueService.open(id);
    }
}
