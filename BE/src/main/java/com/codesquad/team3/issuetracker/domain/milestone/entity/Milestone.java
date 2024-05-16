package com.codesquad.team3.issuetracker.domain.milestone.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class Milestone {

    @Id
    private Integer id;
    private final String title;
    private final String description;
    private final LocalDate deadline;

    private boolean isClosed;
    private boolean isDeleted;

}
