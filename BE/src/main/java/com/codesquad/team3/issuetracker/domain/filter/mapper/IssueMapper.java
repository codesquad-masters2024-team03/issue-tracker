package com.codesquad.team3.issuetracker.domain.filter.mapper;

import com.codesquad.team3.issuetracker.domain.filter.dto.FilterDto;
import com.codesquad.team3.issuetracker.domain.filter.dto.SearchedIssue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IssueMapper {

    List<SearchedIssue> filter(FilterDto filterDto);

    List<SearchedIssue> search(String keyword);
}
