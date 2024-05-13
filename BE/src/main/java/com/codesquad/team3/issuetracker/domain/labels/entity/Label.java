package com.codesquad.team3.issuetracker.domain.labels.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Label {

    @Id
    private final String title;
    private final String description;
    private final String color;
    private final LocalDateTime createTime;
    private boolean isDeleted;

}