package com.codesquad.team3.issuetracker.domain.filter.service;

import com.codesquad.team3.issuetracker.domain.filter.dto.FilterDto;
import com.codesquad.team3.issuetracker.domain.filter.dto.SearchedIssue;
import com.codesquad.team3.issuetracker.domain.filter.mapper.IssueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final IssueMapper issueMapper;


    public List<SearchedIssue> filter
            (boolean state, Integer milestone, List<Integer> labels, List<Integer> assignees) {
        FilterDto filterDto = new FilterDto(state, milestone, assignees, labels);

        if(assignees!=null) {
            filterDto.setAssigneesSize(assignees.size());
        }

        if(labels!=null) {
            filterDto.setLabelsSize(labels.size());

        }
        return issueMapper.filter(filterDto);
    }

    public List<SearchedIssue> search(String keyword) {
        return issueMapper.search(keyword);
    }
}
