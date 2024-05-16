package com.codesquad.team3.issuetracker.domain.labels.dto.response;

import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
import lombok.Data;

import java.util.List;

@Data
public class LabelList {

    private int countsOfLabels;
    private List<Label> labelList;

    public LabelList(List<Label> labelList) {
        this.labelList = labelList;
        this.countsOfLabels= labelList.size();
    }
}
