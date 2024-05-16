package com.codesquad.team3.issuetracker.domain.milestone;

import com.codesquad.team3.issuetracker.domain.milestone.dto.request.MilestoneForm;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneList;
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
    public ResponseEntity<Milestone> create(@RequestBody @Validated MilestoneForm form,
                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()){

        }

        Milestone milestone =
                new Milestone(form.getTitle(), form.getDescription(), form.getDeadLine());
        milestoneServiceImpl.create(milestone);

        return ResponseEntity.ok(milestone);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Milestone> get(@PathVariable("id") Integer id) {

        Milestone milestone = milestoneServiceImpl.getMilestone(id);
        return ResponseEntity.ok(milestone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Milestone> update(@PathVariable("id") Integer id,
                                        @RequestBody @Validated MilestoneForm form) {
        Milestone newMilestone = new Milestone(form.getTitle(), form.getDescription(), form.getDeadLine());
        milestoneServiceImpl.update(id, newMilestone);

        return ResponseEntity.ok(newMilestone);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        milestoneServiceImpl.delete(id);
    }


    @GetMapping
    public ResponseEntity<MilestoneList> LabelList(){
        List<Milestone> milestoneList = milestoneServiceImpl.getAllMilestones();

        return ResponseEntity.ok(new MilestoneList(milestoneList));
    }


}
