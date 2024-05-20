package com.codesquad.team3.issuetracker.support.repository;

import com.codesquad.team3.issuetracker.global.entity.SoftDeleteEntity;
import com.codesquad.team3.issuetracker.support.enums.SoftDeleteSearchFlags;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

import static com.codesquad.team3.issuetracker.support.repository.RepositorySupport.getJdbcAggregateTemplate;
import static com.codesquad.team3.issuetracker.support.repository.RepositorySupport.getQuery;


@NoRepositoryBean
public interface SoftDeleteCrudRepository<T extends SoftDeleteEntity, ID> extends
    SimpleCrudRepository<T, ID> {

    Class<T> getType();

    default T softDelete(T entity) {
        entity.delete();
        update(entity);

        return entity;
    }

    default T recover(T entity) {
        entity.recover();
        update(entity);

        return entity;
    }

    @Override
    @Deprecated
    Iterable<T> findAll();

    default Iterable<T> findAll(SoftDeleteSearchFlags flags) {
        return getJdbcAggregateTemplate().findAll(getQuery(flags), getType());
    }

    default int countByDeleteCondition(SoftDeleteSearchFlags flags) {
        return (int) getJdbcAggregateTemplate().count(getQuery(flags), getType());
    }

    default Optional<T> findByIdWithDeleteCondition(ID id, SoftDeleteSearchFlags flags) {
        return getJdbcAggregateTemplate().findOne(getQuery(id, flags), getType());
    }
}


