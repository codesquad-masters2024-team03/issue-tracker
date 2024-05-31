package com.codesquad.team3.issuetracker.domain.issue.entity.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("ASSIGNER")
public class Assigner {

    private Integer assignerId;
    @Column("issue_id")
    private Integer issueId;

    public Assigner(Integer assignerId) {
        this.assignerId = assignerId;
    }
}
