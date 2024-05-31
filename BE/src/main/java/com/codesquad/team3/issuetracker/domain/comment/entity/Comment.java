package com.codesquad.team3.issuetracker.domain.comment.entity;

import com.codesquad.team3.issuetracker.global.entity.SoftDeleteEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("COMMENT")
@Getter
@NoArgsConstructor
public class Comment implements SoftDeleteEntity {

    @Id
    private Integer id;
    @Column("WRITER_ID")
    private Integer writer;
    private String contents;
    private Integer issueId;

    private LocalDateTime createTime;
    private boolean isDeleted;
    private boolean isPrimary;

    public Comment(Integer writer, String contents, Integer issueId, LocalDateTime createTime, boolean isPrimary) {
        this.writer = writer;
        this.contents = contents;
        this.issueId = issueId;
        this.createTime = createTime;
        this.isPrimary = isPrimary;
    }

    public void update(String newContents){
        this.contents=newContents;
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
