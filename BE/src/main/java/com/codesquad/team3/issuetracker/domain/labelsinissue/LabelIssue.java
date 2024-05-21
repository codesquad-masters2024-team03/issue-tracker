package com.codesquad.team3.issuetracker.domain.labelsinissue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@RequiredArgsConstructor
public class LabelIssue {
    @Id
    private final Integer IssueId;
    private final Integer labelId;

}
