package com.codesquad.team3.issuetracker.domain.labels.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Getter
public class LabelForm {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Length(max = 10)
    private final String title;
    private final String description;
    @NotBlank(message ="색은 필수 입력값입니다.")
    private final String color;

}
