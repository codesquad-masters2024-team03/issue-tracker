package com.codesquad.team3.issuetracker.domain.issue.service;

import com.codesquad.team3.issuetracker.domain.comment.dto.response.CommentDetail;
import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueInfo;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueResponse;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.Assigner;
import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.IssueLabel;
import com.codesquad.team3.issuetracker.domain.labels.dto.response.LabelDetail;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberDetail;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneDetail;
import com.codesquad.team3.issuetracker.global.exceptions.NoSuchRecordException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class IssueServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(IssueServiceImplTest.class);
    @Autowired
    private IssueService issueService;

    @Autowired
    private DataSource dataSource;

    @AfterEach
    void tearDown() throws SQLException, IOException {
        resetAutoIncrement();
    }

    @DisplayName("이슈 폼을 받아서 이슈를 생성한다.")
    @Test
    void createIssues() {

        CreateIssue issueForm = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1, 2), List.of(1), 1);

        LocalDateTime createTime = LocalDateTime.now();
        Issue issue = issueService.create(issueForm, createTime);

        assertThat(issue.getId()).isNotNull();

        assertThat(issue)
                .extracting("title", "writer", "milestoneId", "createTime")
                .contains("테스트코드를 만들자", 1, 1, createTime);


        assertThat(issue.getLabels())
                .extracting(IssueLabel::getLabelId)
                .containsExactlyInAnyOrder(1, 2);

        assertThat(issue.getAssignees())
                .extracting(Assigner::getAssignerId)
                .containsExactlyInAnyOrder(1);

        assertThat(issue.getAssignees()).hasSize(1);
        assertThat(issue.getLabels()).hasSize(2);
        assertThat(issue.getMilestoneId()).isEqualTo(1);

    }


    @DisplayName("라벨, 담당자, 마일스톤은 붙이지 않고 이슈를 생성한다.")
    @Test
    void createIssues2() {

        CreateIssue issueForm = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, null, null, null);

        LocalDateTime createTime = LocalDateTime.now();
        Issue issue = issueService.create(issueForm, createTime);

        assertThat(issue.getId()).isNotNull();

        IssueResponse issue1 = issueService.getIssue(1);

        assertThat(issue)
                .extracting("title", "writer", "createTime")
                .contains("테스트코드를 만들자", 1, createTime);

        assertThat(issue1.getMilestone()).isNull();

        assertThat(issue.getLabels()).isEmpty();

        assertThat(issue.getAssignees()).isEmpty();

    }

    @DisplayName("이슈 id로 이슈를 조회한다.")
    @Test
    void getIssues() {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1, 2), List.of(1), 1);

        issueService.create(issueForm1, createTime);
        IssueResponse issue1 = issueService.getIssue(1);

        log.info("title={}, milestone={}", issue1.getIssue().getTitle(), issue1.getMilestone());

        CreateIssue issueForm2 = createIssue("버그를 수정하자", "버그 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm2, createTime);
        IssueResponse issue2 = issueService.getIssue(2);

        testIssue(issue1, "테스트코드를 만들자", 1);
        testIssue(issue2, "버그를 수정하자", 2);

        testLabel(issue1, new Integer[]{1, 2});
        testLabel(issue2, new Integer[]{3});

        testAssignee(issue1, new Integer[]{1});
        testAssignee(issue2, new Integer[]{1});

        testComments(issue1, "테스트 코드는 필수다");
        testComments(issue2, "버그 수정은 필수다");

        testMilestone(issue1, 1);
        testMilestone(issue2, 2);

        testListSize(issue1, 2, 1);
        testListSize(issue2, 1, 1);

    }


    @DisplayName("이슈 id로 이슈를 삭제한다.")
    @Test
    void deleteIssues() {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1, 2), List.of(1), 1);

        issueService.create(issueForm1, createTime);

        CreateIssue issueForm2 = createIssue("버그를 수정하자", "버그 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm2, createTime);

        List<IssueInfo> openIssues = issueService.getOpenIssues();
        assertThat(openIssues.size()).isEqualTo(2);

        issueService.softDelete(1);

        List<IssueInfo> openIssues2 = issueService.getOpenIssues();
        assertThat(openIssues2.size()).isEqualTo(1);
    }


    @DisplayName("열린 이슈들을 가져온다.")
    @Test
    void getOpenIssues() {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1, 2), List.of(1), 1);

        issueService.create(issueForm1, createTime);
        CreateIssue issueForm2 = createIssue("버그를 수정하자", "버그 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm2, createTime);

        CreateIssue issueForm3 = createIssue("내용을 수정하자", "내용 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm3, createTime);
        List<IssueInfo> openIssues = issueService.getOpenIssues();
        assertThat(openIssues.size()).isEqualTo(3);

    }


    @DisplayName("닫힌 이슈들을 가져온다.")
    @Test
    void getClosedIssues() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1, 2), List.of(1), 1);

        issueService.create(issueForm1, createTime);

        CreateIssue issueForm2 = createIssue("버그를 수정하자", "버그 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm2, createTime);

        CreateIssue issueForm3 = createIssue("내용을 수정하자", "내용 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm3, createTime);

        issueService.close(List.of(1));
        List<IssueInfo> closedIssues = issueService.getClosedIssues();

        assertThat(closedIssues.size()).isEqualTo(1);

    }

    @DisplayName("닫힌 이슈들을 가져온다.")
    @Test
    void getClosedIssuesdd() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1, 2), List.of(1), 1);

        issueService.create(issueForm1, createTime);

        CreateIssue issueForm2 = createIssue("버그를 수정하자", "버그 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm2, createTime);

        CreateIssue issueForm3 = createIssue("내용을 수정하자", "내용 수정은 필수다", 2, List.of(3), List.of(1), 2);
        issueService.create(issueForm3, createTime);

        issueService.close(List.of(1));
        List<IssueInfo> closedIssues = issueService.getClosedIssues();

        assertThat(closedIssues.size()).isEqualTo(1);

    }

    @DisplayName("이슈에 라벨을 붙인다.")
    @Test
    void putLabel() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, null, List.of(1), 1);

        issueService.create(issueForm1, createTime);
        issueService.putLabelLater(List.of(1), 1);
        IssueResponse issue = issueService.getIssue(1);


        testLabel(issue, new Integer[]{1});
        assertThat(issue.getLabels()).isNotNull();
        assertThat(issue.getLabels().size()).isEqualTo(1);
    }

    @DisplayName("이슈의 라벨을 삭제한다.")
    @Test
    void deleteLabel() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1, 2), List.of(1), 1);

        issueService.create(issueForm1, createTime);
        issueService.deleteLabel(1, 1);

        IssueResponse issue = issueService.getIssue(1);
        assertThat(issue.getLabels().size()).isEqualTo(1);

    }

    @DisplayName("이슈에 마일스톤을 붙인다.")
    @Test
    void putMilestone() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1), null, null);

        issueService.create(issueForm1, createTime);
        issueService.putMilestone(1, 1);

        IssueResponse issue = issueService.getIssue(1);
        testMilestone(issue, 1);

        assertThat(issue.getMilestone()).isNotNull();
//        assertThat(issue.getMilestone().get()).isEqualTo(1);

    }

    @DisplayName("이슈의 마일스톤을 삭제한다.")
    @Test
    void deleteMilestone() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1), null, 1);

        issueService.create(issueForm1, createTime);
        issueService.deleteMilestone(1);
        IssueResponse issue = issueService.getIssue(1);
        assertThat(issue.getMilestone()).isNull();
    }

    @DisplayName("이슈의 제목을 수정한다.")
    @Test
    void updateTitle() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1), null, 1);

        issueService.create(issueForm1, createTime);
        issueService.updateTitle(1, "변경된 제목");
        IssueResponse issue = issueService.getIssue(1);
        assertThat(issue.getIssue().getTitle()).isEqualTo("변경된 제목");
    }

    @DisplayName("이슈의 내용을 수정한다..")
    @Test
    void updateContent() throws NoSuchRecordException {

        LocalDateTime createTime = LocalDateTime.now();
        CreateIssue issueForm1 = createIssue("테스트코드를 만들자", "테스트 코드는 필수다", 1, List.of(1), null, 1);

        issueService.create(issueForm1, createTime);
        issueService.updateContents(1, "변경된 내용");
        IssueResponse issue = issueService.getIssue(1);
        assertThat(issue.getContents()).isEqualTo("변경된 내용");
    }

    private void testMilestone(IssueResponse issue1, int id) {
        assertThat(issue1.getMilestone())
                .extracting(MilestoneDetail::getId)
                .isEqualTo(id);
    }

    private void testListSize(IssueResponse issue1, int labelSize, int assigneeSize) {
        assertThat(issue1.getLabels()).hasSize(labelSize);
        assertThat(issue1.getAssignee()).hasSize(assigneeSize);
    }

    private void testComments(IssueResponse issue1, String content) {
        assertThat(issue1.getComments().get(0))
                .extracting(CommentDetail::getContents)
                .isEqualTo(content);
    }

    private void testAssignee(IssueResponse issue, Integer... ids) {
        assertThat(issue.getAssignee())
                .extracting(MemberDetail::getId)
                .containsExactlyInAnyOrder(ids);
    }

    private void testIssue(IssueResponse issue, String title, Integer writerId) {
        assertThat(issue.getIssue())
                .extracting("title", "writer")
                .contains(title, writerId);
    }

    private void testLabel(IssueResponse issue1, Integer... ids) {
        assertThat(issue1.getLabels())
                .extracting(LabelDetail::getId)
                .containsExactlyInAnyOrder(ids);
    }

    private CreateIssue createIssue(String title, String contents, Integer writer, List<Integer> labels, List<Integer> assigness, Integer milestoneId) {
        return new CreateIssue(title, contents, writer, labels, null, assigness, milestoneId);
    }

    private void resetAutoIncrement() throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("ALTER TABLE issue ALTER COLUMN id RESTART WITH 1");
            statement.execute("ALTER TABLE comment ALTER COLUMN id RESTART WITH 1");
            statement.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
            statement.execute("ALTER TABLE label ALTER COLUMN id RESTART WITH 1");
            statement.execute("ALTER TABLE milestone ALTER COLUMN id RESTART WITH 1");
        }
    }
}