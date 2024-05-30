package com.codesquad.team3.issuetracker.domain.issue.service;

import com.codesquad.team3.issuetracker.domain.assigner.Assigner;
import com.codesquad.team3.issuetracker.domain.comment.dto.request.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.dto.response.CommentDetail;
import com.codesquad.team3.issuetracker.domain.comment.service.CommentService;
import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueInfo;
import com.codesquad.team3.issuetracker.domain.issue.dto.response.IssueResponse;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.domain.issue.repository.IssueRepository;
import com.codesquad.team3.issuetracker.domain.labels.dto.response.LabelDetail;
import com.codesquad.team3.issuetracker.domain.labels.service.LabelService;
import com.codesquad.team3.issuetracker.domain.issuelabel.IssueLabel;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberDetail;
import com.codesquad.team3.issuetracker.domain.member.service.MemberService;
import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import com.codesquad.team3.issuetracker.domain.milestone.repository.MilestoneRepository;
import com.codesquad.team3.issuetracker.global.exceptions.NoSuchRecordException;
import com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags.*;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final CommentService commentService;
    private final LabelService labelService;
    private final MilestoneRepository milestoneRepository;
    private final MemberService memberService;

    @Override
    public void create(CreateIssue createIssue) {

        Issue issue = Issue.toEntity(createIssue);
        Issue savedIssue = issueRepository.insert(issue);

        commentService.create(issue.getId(), CreateComment.toEntity(createIssue, savedIssue), true);
        List<Integer> labels = createIssue.getLabels();

        if (labels != null && !createIssue.getLabels().isEmpty()) {
            Set<IssueLabel> issueLabels = putLabel(labels);
            savedIssue.setLabels(issueLabels);
        }

        if (createIssue.getAssignee() != null && !createIssue.getAssignee().isEmpty()) {
            Set<Assigner> assignees = putAssignee(createIssue.getAssignee());
            savedIssue.setAssignees(assignees);
        }

        issueRepository.update(savedIssue);
    }


    @Override
    public void close(List<Integer> issueIds) throws NoSuchRecordException {
        for (Integer issueId : issueIds) {
            Optional<Issue> findIssue = issueRepository.findByIdWithOpenCondition(issueId, OPEN);
            issueRepository.close(findIssue.orElseThrow(NoSuchRecordException::new));
        }
    }

    @Override
    public void open(List<Integer> issueIds) throws NoSuchRecordException {
        for (Integer issueId : issueIds) {
            Optional<Issue> findIssue = issueRepository.findByIdWithOpenCondition(issueId, CLOSE);
            issueRepository.open(findIssue.orElseThrow(NoSuchRecordException::new));
        }
    }

    @Override
    public List<IssueInfo> getOpenIssues() {
        List<Issue> findIssues = issueRepository.findAllOpenIssues();

        return getIssueInfos(findIssues);
    }

    @Override
    public List<IssueInfo> getClosedIssues() {
        List<Issue> findIssues = issueRepository.findAllCloseIssues();

        return getIssueInfos(findIssues);
    }



    @Override
    public int getIssueCount(OpenCloseSearchFlags flags) {
        return issueRepository.countByCloseCondition(flags);

    }

    @Override
    public List<Issue> getIssueByMilestoneId(Integer milestoneId) {
        return issueRepository.getIssuesByMilestoneId(milestoneId);
    }

    @Override
    public IssueResponse getIssue(Integer id) {
        List<CommentDetail> comments = commentService.findComments(id);
        Issue issue = issueRepository.findById(id).orElseThrow();
        List<LabelDetail> label = getLabel(issue);
        Milestone milestone = milestoneRepository.findById(issue.getMilestoneId()).orElseThrow();
        List<MemberDetail> assignees = getMember(issue);

        return IssueResponse.toEntity(issue, comments, assignees, label, milestone);
    }

    @Override
    public void putAssigneeLater(List<Integer> assigneeIndex, Integer id) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        Set<Assigner> assigners = putAssignee(assigneeIndex);
        issue.addAssignee(assigners);
        issueRepository.update(issue);
    }

    @Override
    public void putLabelLater(List<Integer> labelIndex, Integer id) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        Set<IssueLabel> labels = putLabel(labelIndex);
        issue.addLables(labels);
        issueRepository.update(issue);
    }

    @Override
    public void putMilestone(Integer id, Integer milestone) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        issue.setMilestoneId(milestone);
        issueRepository.update(issue);
    }

    @Override
    public void deleteAssignee(Integer id, Integer assignee) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        issue.deleteAssignee(assignee);
        issueRepository.update(issue);
    }

    @Override
    public void deleteLabel(Integer id, Integer label) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        issue.deleteLabel(label);
        issueRepository.update(issue);
    }

    @Override
    public void deleteMilestone(Integer id) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        issue.deleteMilestone();
        issueRepository.update(issue);
    }

    @Override
    public void updateTitle(Integer id, String newTitle) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        issue.setTitle(newTitle);
        issueRepository.update(issue);
    }

    @Override
    public void updateContents(Integer id, String newContents) {
        commentService.updatePrimary(id, newContents);
    }


    private Set<IssueLabel> putLabel(List<Integer> list){

        Set<IssueLabel> set = new HashSet<>();
        for(Integer labelId : list){
            set.add(new IssueLabel(labelId));
        }
        return set;
    }

    private Set<Assigner> putAssignee(List<Integer> list){
        Set<Assigner> set = new HashSet<>();
        for(Integer assignerId : list){
            set.add(new Assigner(assignerId));
        }
        return set;
    }

    private List<MemberDetail> getMember(Issue issue){

        Set<Assigner> assignee = issue.getAssignees();
        return assignee.stream().map(i->memberService.findById(i.getAssignerId()))
                .map(i->new MemberDetail(i.getId(), i.getMemberId())).toList();
    }

    private List<LabelDetail> getLabel(Issue issue) {
        Set<IssueLabel> issueLabel = issue.getLabels();
        return issueLabel.stream()
                .map(i -> labelService.findById(i.getLabelId()))
                .map(LabelDetail::toEntity).toList();
    }

    private List<IssueInfo> getIssueInfos(List<Issue> issues) {
        return issues.stream().map(issue -> {
            List<LabelDetail> label = getLabel(issue);

            if (issue.getMilestoneId() != null){
                Optional<Milestone> milestone = milestoneRepository.findById(issue.getMilestoneId());
                return milestone.map(value -> IssueInfo.toEntity(issue, label, value)).orElseGet(() -> IssueInfo.toEntity(issue, label));
            }else {
                return IssueInfo.toEntity(issue, label);
            }
        }).toList();
    }
}
