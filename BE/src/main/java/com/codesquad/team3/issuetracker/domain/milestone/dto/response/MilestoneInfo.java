package com.codesquad.team3.issuetracker.domain.milestone.dto.response;

import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MilestoneInfo {


    private Milestone milestone;
    private MilestoneProgress progress;

}
