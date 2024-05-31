package com.codesquad.team3.issuetracker.domain.issue.entity.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("LABELS_IN_ISSUE")
@Getter
@AllArgsConstructor
public class IssueLabel {

    private Integer labelId;


}
