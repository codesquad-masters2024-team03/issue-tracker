package com.codesquad.team3.issuetracker.domain.issue.service;

import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.Assigner;
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
import com.codesquad.team3.issuetracker.domain.issue.entity.mapping.IssueLabel;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberDetail;
import com.codesquad.team3.issuetracker.domain.member.service.MemberService;
import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import com.codesquad.team3.issuetracker.domain.milestone.repository.MilestoneRepository;
import com.codesquad.team3.issuetracker.global.exceptions.NoSuchRecordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags.*;
import static com.codesquad.team3.issuetracker.support.enums.SoftDeleteSearchFlags.NOT_DELETED;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final CommentService commentService;
    private final LabelService labelService;
    private final MemberService memberService;

    private final MilestoneRepository milestoneRepository;

    @Override
    public Issue create(CreateIssue createIssue, LocalDateTime createTime) {

        Issue issue = Issue.toEntity(createIssue, createTime);
        Issue savedIssue = issueRepository.insert(issue);

        commentService.create(issue.getId(), CreateComment.toEntity(createIssue, savedIssue), true);
        List<Integer> labels = createIssue.getLabels();

        Set<IssueLabel> issueLabels = putLabel(labels);
        savedIssue.setLabels(issueLabels);


        Set<Assigner> assignees = putAssignee(createIssue.getAssignee());
        savedIssue.setAssignees(assignees);

        issueRepository.update(savedIssue);

        return issue;
    }

    @Override
    public void softDelete(Integer issueId) {
        Issue issue = findIssue(issueId);
        issueRepository.softDelete(issue);
    }

    @Override
    public void restore(Integer issueId) {
        Issue issue = findIssue(issueId);
        issueRepository.recover(issue);

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
        List<Issue> issues = (List<Issue>) issueRepository.findAll(NOT_DELETED, OPEN);
        Collections.reverse(issues);

        return getIssueInfos(issues);
    }

    @Override
    public List<IssueInfo> getClosedIssues() {
        List<Issue> issues = (List<Issue>) issueRepository.findAll(NOT_DELETED, CLOSE);
        Collections.reverse(issues);
        return getIssueInfos(issues);
    }


    @Override
    public List<Issue> getIssueByMilestoneId(Integer milestoneId) {
        return issueRepository.getIssuesByMilestoneId(milestoneId);
    }

    @Override
    public IssueResponse getIssue(Integer id) {
        List<CommentDetail> comments = commentService.findComments(id);
        Issue issue = findIssue(id);
        List<LabelDetail> label = getLabel(issue);
        Milestone milestone = getMilestone(issue);

        List<MemberDetail> assignees = getMember(issue);

        return IssueResponse.toEntity(issue, comments, assignees, label, milestone);
    }

    @Override
    public void putAssigneeLater(List<Integer> assigneeIndex, Integer id) {
        Issue issue = findIssue(id);
        Set<Assigner> assigners = putAssignee(assigneeIndex);
        issue.addAssignees(assigners);
        issueRepository.update(issue);
    }

    @Override
    public void putLabelLater(List<Integer> labelIndex, Integer id) {
        Issue issue = findIssue(id);
        Set<IssueLabel> labels = putLabel(labelIndex);
        issue.addLables(labels);
        issueRepository.update(issue);
    }

    @Override
    public void putMilestone(Integer id, Integer milestone) {
        Issue issue = findIssue(id);
        issue.setMilestoneId(milestone);
        issueRepository.update(issue);
    }

    @Override
    public void deleteAssignee(Integer id, Integer assignee) {
        Issue issue = findIssue(id);
        issue.deleteAssignee(assignee);
        issueRepository.update(issue);
    }

    @Override
    public void deleteLabel(Integer id, Integer label) {
        Issue issue = findIssue(id);
        issue.deleteLabel(label);
        issueRepository.update(issue);
    }

    @Override
    public void deleteMilestone(Integer id) {
        Issue issue = findIssue(id);
        issue.deleteMilestone();
        issueRepository.update(issue);
    }

    @Override
    public void updateTitle(Integer id, String newTitle) {
        Issue issue = findIssue(id);
        issue.setTitle(newTitle);
        issueRepository.update(issue);
    }

    @Override
    public void updateContents(Integer id, String newContents) {
        commentService.updatePrimary(id, newContents);
    }


    private Set<IssueLabel> putLabel(List<Integer> list) {

        Set<IssueLabel> set = new HashSet<>();

        if(list !=null) {
            for (Integer labelId : list) {
                set.add(new IssueLabel(labelId));
            }
        }
        return set;
    }

    private Set<Assigner> putAssignee(List<Integer> list) {
        Set<Assigner> set = new HashSet<>();
        if(list!=null) {
            for (Integer assignerId : list) {
                set.add(new Assigner(assignerId));
            }
        }
        return set;
    }

    private List<MemberDetail> getMember(Issue issue) {

        Set<Assigner> assignee = issue.getAssignees();
        return assignee.stream().map(i -> memberService.findById(i.getAssignerId()))
                .map(i -> new MemberDetail(i.getId(), i.getMemberId())).toList();
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
            Milestone milestone = getMilestone(issue);
            return IssueInfo.toEntity(issue, label, milestone);
        }).toList();
    }

    private Issue findIssue(Integer issueId) {
        return issueRepository.findById(issueId).orElseThrow();
    }

    private Milestone getMilestone(Issue issue) {
        Milestone milestone = null;

        if(issue.getMilestoneId()!=null){
            milestone = milestoneRepository.findById(issue.getMilestoneId()).get();
        }
        return milestone;
    }
}
