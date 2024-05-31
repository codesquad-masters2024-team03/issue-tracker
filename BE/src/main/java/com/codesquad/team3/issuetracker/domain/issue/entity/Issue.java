package com.codesquad.team3.issuetracker.domain.issue.entity;

import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.Assigner;
import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.IssueLabel;
import com.codesquad.team3.issuetracker.global.entity.OpenCloseEntity;
import com.codesquad.team3.issuetracker.global.entity.SoftDeleteEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table("ISSUE")
@Getter
@Setter
@NoArgsConstructor
public class Issue implements OpenCloseEntity, SoftDeleteEntity {

    @Id
    private Integer id;
    @Column("WRITER_ID")
    private Integer writer;
    private String title;
    private LocalDateTime createTime;

    private Integer milestoneId;
    private boolean isClosed;
    private boolean isDeleted;

    @MappedCollection(idColumn = "issue_id")
    private Set<IssueLabel> labels = new HashSet<>();

    @MappedCollection(idColumn = "issue_id")
    private Set<Assigner> assignees = new HashSet<>();


    public Issue(Integer writer, String title, Integer milestoneId, LocalDateTime createTime) {
        this.writer = writer;
        this.title = title;
        this.milestoneId = milestoneId;
        this.createTime = createTime;
    }

    public static Issue toEntity(CreateIssue createIssue, LocalDateTime createTime) {

        return new Issue(createIssue.getWriter(),
                createIssue.getTitle(),
                createIssue.getMilestone(),
                createTime);
    }

    public void addLables(Set<IssueLabel> label) {
        labels.addAll(label);
    }

    public void addAssignees(Set<Assigner> assignee) {
        this.assignees.addAll(assignee);
    }

    public void deleteLabel(Integer label) {
        labels.removeIf(i -> i.getLabelId().equals(label));
    }

    public void deleteAssignee(Integer assignee) {
        assignees.removeIf(i -> i.getAssignerId().equals(assignee));
    }

    public void deleteMilestone() {
        milestoneId = null;
    }

    @Override
    public void close() {
        this.isClosed = true;
    }

    @Override
    public void open() {
        this.isClosed = false;
    }

    @Override
    public boolean isClosed() {
        return this.isClosed;
    }

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public void recover() {
        this.isDeleted = false;

    }
}
