package com.codesquad.team3.issuetracker.domain.issue.service;

import com.codesquad.team3.issuetracker.domain.comment.dto.CreateComment;
import com.codesquad.team3.issuetracker.domain.comment.service.CommentService;
import com.codesquad.team3.issuetracker.domain.issue.dto.request.CreateIssue;
import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.domain.issue.repository.IssueRepository;
import com.codesquad.team3.issuetracker.global.exceptions.NoSuchRecordException;
import com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags.*;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final CommentService commentService;

    @Override
    public void createIssue(CreateIssue createIssue) {

        Issue issue = new Issue(
                        createIssue.getWriterId(),
                        createIssue.getTitle(),
                        LocalDateTime.now(),
                        createIssue.getMilestoneId()
                        );


        Issue insertdIssue = issueRepository.insert(issue);
        commentService.createComment(
                new CreateComment(createIssue.getWriterId(),
                createIssue.getContents(),
                insertdIssue.getId(),
                null));
    }

    @Override
    public void open(Integer id) {
        Issue issue = issueRepository.findById(id).get();
        issueRepository.open(issue);
    }

    @Override
    public void close(Integer id) {
        Issue issue = issueRepository.findById(id).get();
        issueRepository.close(issue);
    }


    @Override
    public List<Issue> getAllIssues(int loadCount) {
        return null;
    }

    @Override
    public int getIssueCount(OpenCloseSearchFlags flags) {
        return issueRepository.countByCloseCondition(Issue.class, flags);

    }

    @Override
    public void openIssue(List<Integer> issueIds) throws NoSuchRecordException {
        for (Integer issueId : issueIds) {
            Optional<Issue> findIssue = issueRepository.findByIdWithOpenCondition(issueId, Issue.class, OPEN);
            issueRepository.open(findIssue.orElseThrow(NoSuchRecordException::new));
        }
    }

    @Override
    public void closeIssue(List<Integer> issueIds) throws NoSuchRecordException {
        for (Integer issueId : issueIds) {
            Optional<Issue> findIssue = issueRepository.findByIdWithOpenCondition(issueId, Issue.class, OPEN);
            issueRepository.close(findIssue.orElseThrow(NoSuchRecordException::new));
        }
    }

    @Override
    public List<Issue> getIssueByMilestoneId(Integer milestoneId) {
        return issueRepository.getIssueListByMilestoneId(milestoneId);
    }




}
