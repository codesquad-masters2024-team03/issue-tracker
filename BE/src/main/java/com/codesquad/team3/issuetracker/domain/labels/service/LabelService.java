package com.codesquad.team3.issuetracker.domain.labels.service;

import com.codesquad.team3.issuetracker.domain.labels.dto.request.LabelForm;
import com.codesquad.team3.issuetracker.domain.labels.dto.response.LabelDetail;
import com.codesquad.team3.issuetracker.domain.labels.entity.Label;

import java.util.List;

public interface LabelService {

    void save(LabelForm form);

    void update(Integer id, LabelForm form);

    void delete(Integer id);

    Label findById(Integer id);

    List<LabelDetail> findByIssueId(Integer issueId);
    List<Label> findAll();
}
