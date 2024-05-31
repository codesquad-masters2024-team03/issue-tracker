package com.codesquad.team3.issuetracker.domain.milestone.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MilestoneForm {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
}
