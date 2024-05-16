package com.codesquad.team3.issuetracker.domain.milestone.dto.response;

import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import lombok.Getter;

import java.util.List;

@Getter
public class MilestoneList {

    private final int countOfMilestone;
    private final List<Milestone> milestoneList;


    public MilestoneList(List<Milestone> milestoneList) {
        this.milestoneList = milestoneList;
        this.countOfMilestone=milestoneList.size();
    }
}
