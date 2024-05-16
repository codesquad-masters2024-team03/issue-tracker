package com.codesquad.team3.issuetracker.domain.labels.service;

import com.codesquad.team3.issuetracker.domain.labels.entity.Label;

import java.util.List;

public interface LabelService {

    public void create(Label label);

    public void update(Integer id, Label updateLabel);

    public void delete(Integer id);

    public Label getLabel(Integer id);

    public List<Label> getAllLabels();
}
