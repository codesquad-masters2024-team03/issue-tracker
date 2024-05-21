package com.codesquad.team3.issuetracker.domain.milestone.service;

import com.codesquad.team3.issuetracker.domain.issue.entity.Issue;
import com.codesquad.team3.issuetracker.domain.issue.service.IssueService;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneInfo;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneProgress;
import com.codesquad.team3.issuetracker.domain.milestone.dto.response.MilestoneResponse;
import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import com.codesquad.team3.issuetracker.domain.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags.CLOSE;
import static com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags.OPEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final IssueService issueService;

    @Override
    public void create(Milestone milestone) {
        milestoneRepository.insert(milestone);
    }

    @Override
    public void delete(Integer id) {
        milestoneRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, Milestone updatemilestone) {
        milestoneRepository.update(new Milestone(id, updatemilestone.getTitle(), updatemilestone.getDescription(),
                updatemilestone.getDeadline()));
    }

    @Override
    public void close(Integer id) {
        Milestone milestone = milestoneRepository.findById(id).get();
        milestoneRepository.close(milestone);
    }

    @Override
    public MilestoneResponse getMilestone(Integer id) {
        Milestone milestone = milestoneRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return new MilestoneResponse(milestone.getId(), milestone.getTitle(), milestone.getDescription(), milestone.getDeadline());
    }

    @Override
    public List<MilestoneInfo> getOpenMilestones() {

        List<Milestone> openmilestones = (List<Milestone>) milestoneRepository.findAll(Milestone.class, OPEN);
        List<MilestoneInfo> opens = new ArrayList<>();
        for (Milestone milestone : openmilestones) {
            MilestoneInfo milestoneInfo = createMilestoneInfo(milestone);
            opens.add(milestoneInfo);
        }

        return opens;
    }

    @Override
    public List<MilestoneInfo> getClosedMilestones() {
        List<Milestone> closedList = (List<Milestone>) milestoneRepository.findAll(Milestone.class, CLOSE);
        List<MilestoneInfo> closed = new ArrayList<>();
        for (Milestone milestone : closedList) {
            MilestoneInfo milestoneInfo = createMilestoneInfo(milestone);
            closed.add(milestoneInfo);

        }

        return closed;
    }


    public MilestoneInfo createMilestoneInfo(Milestone milestone) {

        List<Issue> issueList = issueService.getIssueByMilestoneId(milestone.getId());
        int size = issueList.size();
        int close = 0;
        int progress = 0;

        for (Issue issue : issueList) {
            if (issue.isClosed()) {
                close = close + 1;
            }
        }

        if (size > 0) {
            progress = (int) (((double) close / size) * 100);
        } else {
            progress = 0;
        }
        int open = size - close;
        return new MilestoneInfo(milestone, new MilestoneProgress(close, open, progress));
    }

}
