package com.codesquad.team3.issuetracker.support.repository;

import com.codesquad.team3.issuetracker.global.entity.OpenCloseEntity;
import com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

import static com.codesquad.team3.issuetracker.support.repository.RepositorySupport.*;

@NoRepositoryBean
public interface OpenCloseCrudRepository<T extends OpenCloseEntity, ID> extends SimpleCrudRepository<T, ID>{

    default T open(T entity) {
        entity.open();
        update(entity);

        return entity;
    }

    default T close(T entity) {
        entity.close();
        update(entity);

        return entity;
    }

    Class<T> getType();

    default Iterable<T> findAll(OpenCloseSearchFlags flags) {
        return getJdbcAggregateTemplate().findAll(getQuery(flags), getType());
    }

    default int countByCloseCondition(OpenCloseSearchFlags flags) {
        return (int) getJdbcAggregateTemplate().count(getQuery(flags), getType());
    }

    default Optional<T> findByIdWithOpenCondition(ID id, OpenCloseSearchFlags flags) {
        return getJdbcAggregateTemplate().findOne(getQuery(id, flags), getType());
    }

}

