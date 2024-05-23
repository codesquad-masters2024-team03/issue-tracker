package com.codesquad.team3.issuetracker.domain.issuelabel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="labels_in_issue")
@Getter
@RequiredArgsConstructor
public class IssueLabel {

    private final Integer labelId;


}
