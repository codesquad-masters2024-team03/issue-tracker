package com.codesquad.team3.issuetracker.domain.comment.entity;

import com.codesquad.team3.issuetracker.domain.file.entity.UploadFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("comment")
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    private Integer id;
    private Integer writerId;
    private String contents;
    private Integer issueId;
    private UploadFile uploadFile;
    private LocalDateTime createTime;
    private boolean isDeleted;
    private boolean isPrimary;

    public Comment(Integer writerId, String contents, Integer issueId, UploadFile uploadFile, LocalDateTime createTime) {
        this.writerId = writerId;
        this.contents = contents;
        this.issueId = issueId;
        this.uploadFile = uploadFile;
        this.createTime = createTime;
    }

    public Comment(Integer id, Integer writerId, String contents, Integer issueId, UploadFile uploadFile) {
        this.id = id;
        this.writerId = writerId;
        this.contents = contents;
        this.issueId = issueId;
        this.uploadFile = uploadFile;
    }
}
