package com.codesquad.team3.issuetracker.domain.filter.dto;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FilterDto {

    private final boolean state;
    private final Integer milestone;
    private final List<Integer> assignees;
    private final List<Integer> labels;
    private int assigneesSize;
    private int labelsSize;


}
