package com.codesquad.team3.issuetracker.domain.issue.entity;

import com.codesquad.team3.issuetracker.global.entity.OpenCloseEntity;
import com.codesquad.team3.issuetracker.global.entity.SoftDeleteEntity;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


import java.time.LocalDateTime;

@Table("ISSUE")
@Getter
public class Issue implements SoftDeleteEntity, OpenCloseEntity {

    @Id
    private Integer id;
    private Integer writerId;
    private String title;
    private LocalDateTime createTime;

    private Integer milestoneId;
    private boolean isClosed;
    private boolean isDeleted;


    public Issue(Integer writerId, String title, LocalDateTime createTime, Integer milestoneId) {
        this.writerId = writerId;
        this.title = title;
        this.createTime = createTime;
        this.milestoneId = milestoneId;
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
