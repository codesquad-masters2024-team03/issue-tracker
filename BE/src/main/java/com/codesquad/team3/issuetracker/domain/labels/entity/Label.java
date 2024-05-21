package com.codesquad.team3.issuetracker.domain.labels.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name ="label")
@Getter
@NoArgsConstructor
public class Label {

    @Id
    private Integer id;
    private String title;
    private String description;
    private String color;
    private String fontColor;

    public Label(String title, String description, String color, String fontColor) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.fontColor=fontColor;
    }

    public Label(Integer id, String title, String description, String color, String fontColor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.fontColor=fontColor;
    }
}
