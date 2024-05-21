package com.codesquad.team3.issuetracker.domain.labels.service;

import com.codesquad.team3.issuetracker.domain.labels.dto.request.LabelForm;
import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
import com.codesquad.team3.issuetracker.domain.labels.enums.ColorCode;
import com.codesquad.team3.issuetracker.domain.labels.repository.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public void save(LabelForm form) {

        Label label = new Label(form.getTitle(), form.getDescription(), form.getColor(), form.getFontColor());
        labelRepository.save(label);
    }

    @Override
    public void update(Integer id, LabelForm form) {

        labelRepository.update(new Label(id, form.getTitle(), form.getDescription(), form.getColor(), form.getFontColor()));

    }


    @Override
    public void delete(Integer id) {
        labelRepository.deleteById(id);
    }

    @Override
    public Label findById(Integer id) {
        return labelRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Label> findAll() {
        return StreamSupport.stream(labelRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
