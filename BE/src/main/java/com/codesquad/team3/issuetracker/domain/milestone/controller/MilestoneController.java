package com.codesquad.team3.issuetracker.domain.milestone;

import com.codesquad.team3.issuetracker.domain.milestone.dto.request.MilestoneForm;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneInfo;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneResponse;
import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import com.codesquad.team3.issuetracker.domain.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/milestones")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneServiceImpl;

    @PostMapping("")
    public void create(@RequestBody @Validated MilestoneForm form,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

        }

        Milestone milestone =
                new Milestone(form.getTitle(), form.getDescription(), form.getDeadLine());
        milestoneServiceImpl.create(milestone);

    }

    @GetMapping("/{id}")
    public ResponseEntity<MilestoneResponse> get(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(milestoneServiceImpl.getMilestone(id));
    }

    @PutMapping("/{id}")
    public void updateById(@PathVariable("id") Integer id,
                       @RequestBody @Validated MilestoneForm form) {
        Milestone newMilestone = new Milestone(form.getTitle(), form.getDescription(), form.getDeadLine());
        milestoneServiceImpl.update(id, newMilestone);

    }

    @PutMapping("/close/{id}")
    public void close(@PathVariable Integer id) {
        milestoneServiceImpl.close(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        milestoneServiceImpl.delete(id);
    }


    @GetMapping("/open")
    public ResponseEntity<List<MilestoneInfo>> getOpenMilestoneList() {
        List<MilestoneInfo> openMilestones = milestoneServiceImpl.getOpenMilestones();
        return ResponseEntity.ok(openMilestones);
    }


    @GetMapping("/close")
    public ResponseEntity<List<MilestoneInfo>> getClosedMilestoneList() {
        List<MilestoneInfo> closedMilestones = milestoneServiceImpl.getClosedMilestones();

        return ResponseEntity.ok(closedMilestones);
    }

}




