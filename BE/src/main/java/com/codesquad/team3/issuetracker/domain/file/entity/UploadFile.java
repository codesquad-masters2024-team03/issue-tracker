package com.codesquad.team3.issuetracker.domain.file.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("uploaded_file_in_comment")
@RequiredArgsConstructor
public class UploadFile {

    @Id
    private final Integer commentId;
    private final String file;


}
