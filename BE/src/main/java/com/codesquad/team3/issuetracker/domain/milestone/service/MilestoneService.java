package com.codesquad.team3.issuetracker.domain.milestone.service;

import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneInfo;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneResponse;
import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;

import java.util.List;

public interface MilestoneService {

    void create(Milestone milestone);

    void delete(Integer id);

    void update(Integer id, Milestone updatemilestone);
    void close(Integer id);

    MilestoneResponse getMilestone(Integer id);

    List<MilestoneInfo> getOpenMilestones();

    List<MilestoneInfo> getClosedMilestones();

}
