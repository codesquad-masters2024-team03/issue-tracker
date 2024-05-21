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

    default Iterable<T> findAll(Class<T> entityClass, OpenCloseSearchFlags flags) {
        return getJdbcAggregateTemplate().findAll(getQuery(flags), entityClass);
    }

    default int countByCloseCondition(Class<T> entityClass, OpenCloseSearchFlags flags) {
        return (int) getJdbcAggregateTemplate().count(getQuery(flags), entityClass);
    }

    default Optional<T> findByIdWithOpenCondition(ID id, Class<T> entityClass, OpenCloseSearchFlags flags) {
        return getJdbcAggregateTemplate().findOne(getQuery(id, flags), entityClass);
    }

}

