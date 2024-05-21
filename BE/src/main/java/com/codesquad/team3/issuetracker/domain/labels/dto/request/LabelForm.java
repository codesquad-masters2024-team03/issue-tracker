package com.codesquad.team3.issuetracker.domain.labels.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@RequiredArgsConstructor
@Getter
@Setter
public class LabelForm {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Length(max = 10)
    private final String title;
    private final String description;
    @NotBlank(message ="색은 필수 입력값입니다.")
    private final String color;
    private final String fontColor;
}
