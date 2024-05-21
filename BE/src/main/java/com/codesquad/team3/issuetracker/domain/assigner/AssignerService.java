package com.codesquad.team3.issuetracker.domain.assigner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignerService {

    private final AssignerRepository assignerRepository;

    public void create(Integer issueId, Integer assigneeId){
        assignerRepository.insert(new Assigner(issueId, assigneeId));
    }

}
