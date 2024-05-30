package com.codesquad.team3.issuetracker.domain.issue.entity;

import com.codesquad.team3.issuetracker.domain.assigner.Assigner;
import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issuelabel.IssueLabel;
import com.codesquad.team3.issuetracker.global.entity.OpenCloseEntity;
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

@Table(name="ISSUE")
@Getter
@Setter
@NoArgsConstructor
public class Issue implements OpenCloseEntity {

    @Id
    private Integer id;
    @Column("writer_id")
    private Integer writerId;
    private String title;
    @Column("create_time")
    private LocalDateTime createTime;

    @Column("milestone_id")
    private Integer milestoneId;
    @Column("is_closed")
    private boolean isClosed;
    @Column("is_deleted")
    private boolean isDeleted;

    @MappedCollection(idColumn = "issue_id")
    private Set<IssueLabel> labels = new HashSet<>();

    @MappedCollection(idColumn = "issue_id")
    private Set<Assigner> assignees = new HashSet<>();


    public Issue(Integer writerId, String title, LocalDateTime createTime, Integer milestoneId) {
        this.writerId = writerId;
        this.title = title;
        this.createTime = createTime;
        this.milestoneId = milestoneId;
    }public Issue(Integer writerId, String title, LocalDateTime createTime) {
        this.writerId = writerId;
        this.title = title;
        this.createTime = createTime;
    }

    public static Issue toEntity(CreateIssue createIssue) {

        if (createIssue.getMilestone() == null) {
            return new Issue(
                    createIssue.getWriter(),
                    createIssue.getTitle(),
                    LocalDateTime.now()
            );
        }

        return new Issue(createIssue.getWriter(),
                createIssue.getTitle(),
                LocalDateTime.now(),
                createIssue.getMilestone());
    }

    public void addLables(Set<IssueLabel> label){
        labels.addAll(label);
    }

    public void addAssignee(Set<Assigner> assignee){
        this.assignees.addAll(assignee);
    }

    public void deleteLabel(Integer label){
        labels.removeIf(i->i.getLabelId().equals(label));
    }

    public void deleteAssignee(Integer assignee){
        assignees.removeIf(i->i.getAssignerId().equals(assignee));
    }

    public void deleteMilestone(){
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


}
