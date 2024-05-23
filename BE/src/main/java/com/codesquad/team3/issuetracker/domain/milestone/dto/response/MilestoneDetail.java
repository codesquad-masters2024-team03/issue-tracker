package com.codesquad.team3.issuetracker.domain.milestone.dto.response;

import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MilestoneDetail {

    private Integer id;
    private String title;
    private String description;
    private LocalDate deadline;

    public static MilestoneDetail toEntity(Milestone milestone) {
        return new MilestoneDetail(milestone.getId(), milestone.getTitle(), milestone.getDescription(), milestone.getDeadline());

    }
}
