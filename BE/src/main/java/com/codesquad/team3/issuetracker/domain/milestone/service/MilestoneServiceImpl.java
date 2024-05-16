package com.codesquad.team3.issuetracker.domain.milestone.service;

import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import com.codesquad.team3.issuetracker.domain.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;


    public void create(Milestone milestone) {
        milestoneRepository.insert(milestone);
    }

    public void delete(Integer id) {
        milestoneRepository.deleteById(id);
    }


    public void update(Integer id, Milestone updatemilestone) {
        milestoneRepository.updateById(id,
                updatemilestone.getTitle(),
                updatemilestone.getDescription(),
                updatemilestone.getDeadline());
    }

    public Milestone getMilestone(Integer id) {
        return milestoneRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Milestone> getAllMilestones() {
        return StreamSupport.stream(milestoneRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


}
