package com.codesquad.team3.issuetracker.domain.labels.repository;

import com.codesquad.team3.issuetracker.domain.labels.entity.Label;
import com.codesquad.team3.issuetracker.support.repository.GlobalCrudRepository;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends GlobalCrudRepository<Label, Integer> {


    @Modifying
    @Query("UPDATE label SET title = :title, description = :description, color =:color WHERE id = :id")
    void updateById(Integer id, String title, String description, String color);
}
