package com.codesquad.team3.issuetracker.domain.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private Integer id;
    @Column("writer_id")
    private Integer writer;
    private String contents;
    private Integer issueId;

    private LocalDateTime createTime;
    private boolean isDeleted;
    private boolean isPrimary;

    public Comment(Integer writer, String contents, Integer issueId,  LocalDateTime createTime, boolean isPrimary) {
        this.writer = writer;
        this.contents = contents;
        this.issueId = issueId;

        this.createTime = createTime;
        this.isPrimary = isPrimary;
    }

    public Comment(Integer id, Integer writer, String contents, Integer issueId, LocalDateTime createTime) {
        this.id = id;
        this.writer = writer;
        this.contents = contents;
        this.issueId = issueId;
        this.createTime = createTime;

    }

    public static Comment  updatedComment(Comment comment, String newContents){
        return new Comment(comment.getId(), comment.getWriter(), newContents, comment.getIssueId(), comment.getCreateTime(),
                comment.isDeleted(), comment.isPrimary());
    }

}
