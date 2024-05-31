package com.codesquad.team3.issuetracker.domain.milestone.dto.response;


import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MilestoneInfo {

    private Integer id;
    private String title;
    private String description;
    private List<LocalDate> deadline;
    private int countOfClosedIssue;
    private int countOfOpenIssue;

    public static MilestoneInfo toEntity(Milestone milestone, int close, int open) {
        if(milestone.getDeadline()==null){
            return createEntity(milestone, List.of(), close, open);
        }
        return createEntity(milestone, List.of(milestone.getDeadline()), close, open);


    }

    private static MilestoneInfo createEntity(Milestone milestone, List<LocalDate> deadline, int close, int open){
        return new MilestoneInfo(milestone.getId(), milestone.getTitle(), milestone.getDescription(), deadline, close, open);
    }
}
