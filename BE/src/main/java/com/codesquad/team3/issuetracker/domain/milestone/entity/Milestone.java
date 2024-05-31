package com.codesquad.team3.issuetracker.domain.milestone.entity;

import com.codesquad.team3.issuetracker.global.entity.OpenCloseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Table("MILESTONE")
public class Milestone implements OpenCloseEntity {

    @Id
    private Integer id;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean isClosed;

    @Override
    public void close() {
        isClosed=true;
    }

    @Override
    public void open() {
        isClosed=false;
    }

    public Milestone(Integer id, String title, String description, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public Milestone(String title, String description, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
}
