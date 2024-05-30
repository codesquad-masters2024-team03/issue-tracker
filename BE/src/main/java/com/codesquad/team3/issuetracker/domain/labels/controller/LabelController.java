package com.codesquad.team3.issuetracker.domain.labels.controller;


import com.codesquad.team3.issuetracker.domain.labels.dto.response.LabelList;
import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
import com.codesquad.team3.issuetracker.domain.labels.dto.request.LabelForm;
import com.codesquad.team3.issuetracker.domain.labels.service.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/labels")
public class LabelController {

    private final LabelService labelService;

    @PostMapping
    public void create(@RequestBody @Validated LabelForm form,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            //유효성 로직
        }

        labelService.save(form);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Label> getById(@PathVariable("id") Integer id) {

        Label label = labelService.findById(id);
        return ResponseEntity.ok(label);
    }

    @PutMapping("/{id}")
    public void updateById(@PathVariable("id") Integer id, @RequestBody @Validated LabelForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            //에러 로직
        }

        labelService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        labelService.delete(id);

    }

    @GetMapping
    public ResponseEntity<LabelList> findAll(){
        List<Label> labels = labelService.findAll();

        return ResponseEntity.ok(new LabelList(labels));
    }
}
