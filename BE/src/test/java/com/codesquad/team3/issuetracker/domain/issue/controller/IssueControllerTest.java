package com.codesquad.team3.issuetracker.domain.issue.controller;

import com.codesquad.team3.issuetracker.domain.comment.repository.CommentRepository;
import com.codesquad.team3.issuetracker.domain.filter.mapper.IssueMapper;
import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.domain.issue.repository.IssueRepository;
import com.codesquad.team3.issuetracker.domain.issue.service.IssueService;
import com.codesquad.team3.issuetracker.domain.labels.repository.LabelRepository;
import com.codesquad.team3.issuetracker.domain.member.repository.MemberRepository;
import com.codesquad.team3.issuetracker.domain.milestone.repository.MilestoneRepository;
import com.codesquad.team3.issuetracker.support.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = IssueController.class)
@Import(TestConfig.class)
class IssueControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private JdbcMappingContext jdbcMappingContext;

    @MockBean
    private MilestoneRepository milestoneRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private IssueRepository issueRepository;

    @MockBean
    private LabelRepository labelRepository;

    @MockBean
    private IssueMapper issueMapper;

    @DisplayName("이슈를 생성한다.")
    @Test
    void createIssue() throws Exception {
        CreateIssue issue = new CreateIssue("이슈를 만들자", "이슈 변경변경", 1, List.of(1, 2), null, List.of(1), 1);

        LocalDateTime now = LocalDateTime.now();
        when(issueService.create(issue, now)).thenReturn(new Issue(1, "이슈를 만들자", 1, now));
        mockMvc.perform(
                post("/api/issues")
                        .content(objectMapper.writeValueAsString(issue))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @DisplayName("이슈를 생성할 때 제목은 필수값이다.")
    @Test
    void createIssueWithoutTitle() throws Exception {
        CreateIssue issue = new CreateIssue(null, "이슈 변경변경", 1, List.of(1, 2), null, List.of(1), 1);

        LocalDateTime now = LocalDateTime.now();
        when(issueService.create(issue, now)).thenReturn(new Issue(1, "이슈를 만들자", 1, now));
        mockMvc.perform(
                post("/api/issues")
                        .content(objectMapper.writeValueAsString(issue))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

