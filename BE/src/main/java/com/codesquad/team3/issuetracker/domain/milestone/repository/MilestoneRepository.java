package com.codesquad.team3.issuetracker.domain.milestone.repository;

import com.codesquad.team3.issuetracker.domain.milestone.entity.Milestone;
import com.codesquad.team3.issuetracker.support.repository.GlobalCrudRepository;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MilestoneRepository extends GlobalCrudRepository<Milestone, Integer> {

    @Modifying
    @Query("UPDATE milestone SET title = :title, description = :description, deadline =:deadline WHERE id = :id")
    void updateById(Integer id, String title, String description, LocalDate deadline);

}
