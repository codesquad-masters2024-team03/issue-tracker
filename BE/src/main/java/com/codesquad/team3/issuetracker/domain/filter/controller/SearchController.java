package com.codesquad.team3.issuetracker.domain.filter.controller;

import com.codesquad.team3.issuetracker.domain.filter.dto.SearchedIssue;
import com.codesquad.team3.issuetracker.domain.filter.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api//issues")
public class SearchController {

    private final SearchService searchService;

    public ResponseEntity<List<SearchedIssue>> filter(
            @RequestParam(required = false, defaultValue = "false") Boolean state,
            @RequestParam(required = false) Integer milestone,
            @RequestParam(required = false) List<Integer> labels,
            @RequestParam(required = false) List<Integer> assignees){

        List<SearchedIssue> result = searchService.filter(state, milestone, labels, assignees);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchedIssue>> search(
            @RequestParam String keyword){
        List<SearchedIssue> search = searchService.search(keyword);

        return ResponseEntity.ok(search);

    }
}
