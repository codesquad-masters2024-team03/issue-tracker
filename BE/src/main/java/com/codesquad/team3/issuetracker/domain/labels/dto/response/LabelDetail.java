package com.codesquad.team3.issuetracker.domain.labels.dto.response;

import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelDetail {

    private Integer id;
    private String title;
    private String color;
    private String fontColor;


        public static LabelDetail toEntity(Label label) {

            return new LabelDetail(label.getId(), label.getTitle(), label.getColor(), label.getFontColor());
    }
}
