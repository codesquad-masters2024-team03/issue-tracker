package com.codesquad.team3.issuetracker.domain.milestone.dto.response;

import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public class MilestoneDetail {

    private Integer id;
    private String title;
    private String description;
    private List<LocalDate> deadline;

    public static List<MilestoneDetail> toEntity(Milestone milestone) {
        if(milestone==null){
           return new ArrayList<>();
        }
        return createEntity(milestone);

    }

    private static List<MilestoneDetail> createEntity(Milestone milestone){
        if(milestone.getDeadline()==null){
            return List.of(new MilestoneDetail(milestone.getId(), milestone.getTitle(), milestone.getDescription(), new ArrayList<>()));
        }

        return List.of(new MilestoneDetail(milestone.getId(), milestone.getTitle(), milestone.getDescription(), List.of(milestone.getDeadline())));
    }
}
