package com.codesquad.team3.issuetracker.domain.labelsinissue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelIssueService {

    private final LabelIssueRepository labelIssueRepository;
    public void create(Integer issueId, Integer labelId){
        labelIssueRepository.insert(new LabelIssue(issueId, labelId));
    }

    public LabelIssue get(Integer issueId){
        return labelIssueRepository.findById(issueId).get();
    }
}
