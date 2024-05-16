package com.codesquad.team3.issuetracker.domain.labels.service;

import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
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

    public void create(Label label){
        labelRepository.save(label);
    }

    public void update(Integer id, Label updateLabel){
        labelRepository.updateById(id,
                updateLabel.getTitle(),
                updateLabel.getDescription(),
                updateLabel.getColor());
    }

    public void delete(Integer id){
        labelRepository.deleteById(id);
    }


    public Label getLabel(Integer id) {
        return labelRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Label> getAllLabels(){
        return StreamSupport.stream(labelRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
