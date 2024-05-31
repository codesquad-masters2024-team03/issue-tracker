package com.codesquad.team3.issuetracker.domain.issue.controller;

import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueInfo;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueResponse;
import com.codesquad.team3.issuetracker.domain.issue.service.IssueService;
import com.codesquad.team3.issuetracker.global.exceptions.NoSuchRecordException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public void create(@RequestBody @Valid CreateIssue createIssue, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

        }
        issueService.create(createIssue, LocalDateTime.now());
    }

    @PutMapping("/close/{id}")
    public void close(@PathVariable("id") Integer id) throws NoSuchRecordException {
        issueService.close(Arrays.asList(id));
    }

    @PutMapping("/open/{id}")
    public void open(@PathVariable("id") Integer id) throws NoSuchRecordException {
        issueService.open((Arrays.asList(id)));
    }

    @PutMapping("/close")
    public void closeIssues(@RequestBody List<Integer> id) throws NoSuchRecordException {
        issueService.close(id);
    }

    @PutMapping("/open")
    public void openIssues(@RequestBody List<Integer> id) throws NoSuchRecordException {
        issueService.open(id);
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable("id")Integer id){
        issueService.softDelete(id);
    }

    @PutMapping("/restore/{id}")
    public void restore(@PathVariable("id")Integer id){
        issueService.restore(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponse> get(@PathVariable("id") Integer id) {
        IssueResponse issue = issueService.getIssue(id);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/open")
    public ResponseEntity<List<IssueInfo>> getClosedIssues() {
        List<IssueInfo> openIssues = issueService.getOpenIssues();

        return ResponseEntity.ok(openIssues);
    }

    @GetMapping("/close")
    public ResponseEntity<List<IssueInfo>> getOpenIssues() {
        List<IssueInfo> closedIssues = issueService.getClosedIssues();

        return ResponseEntity.ok(closedIssues);
    }

    @PutMapping("/assignees/{id}")
    public void putAssignee(@PathVariable("id") Integer id, @RequestBody List<Integer> assignees) {
        issueService.putAssigneeLater(assignees, id);

    }

    @PutMapping("/labels/{id}")
    public void putLabel(@PathVariable("id") Integer id, @RequestBody List<Integer> labels) {
        issueService.putLabelLater(labels, id);

    }

    @PutMapping(("/milestones/{id}"))
    public void putMilestone(@PathVariable("id") Integer id, @RequestBody Integer milestone) {
        issueService.putMilestone(id, milestone);
    }

    @DeleteMapping("/assignees/{id}")
    public void deleteAssignee(@PathVariable("id") Integer id, @RequestBody Integer assignee) {
        issueService.deleteAssignee(id, assignee);
    }

    @DeleteMapping("/labels/{id}")
    public void deleteLabel(@PathVariable("id") Integer id, @RequestBody Integer label) {
        issueService.deleteLabel(id, label);
    }

    @DeleteMapping("/milestones/{id}")
    public void deleteMilestone(@PathVariable Integer id) {
        issueService.deleteMilestone(id);
    }

    @PutMapping("/title/{id}")
    public void updateTitle(@PathVariable("id") Integer id, @RequestBody String newTitle) {
        newTitle = newTitle.replaceAll("^\"|\"$", "");
        issueService.updateTitle(id, newTitle);
    }

    @PutMapping("/content/{id}")
    public void updateContents(@PathVariable("id") Integer id, @RequestBody String newContents) {

        newContents = newContents.replaceAll("^\"|\"$", "");
        issueService.updateContents(id, newContents);
    }

}
