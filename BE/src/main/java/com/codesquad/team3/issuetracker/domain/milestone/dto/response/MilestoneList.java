package com.codesquad.team3.issuetracker.domain.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MilestoneList {

    private final int countOfMilestone;
    private final List<MilestoneInfo> milestoneList;


}
