package com.codesquad.team3.issuetracker.support.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import static com.codesquad.team3.issuetracker.support.repository.RepositorySupport.getJdbcAggregateTemplate;

@NoRepositoryBean
public interface SimpleCrudRepository<T, ID> extends CrudRepository<T, ID> {

    @Override
    @Deprecated
    <S extends T> S save(S entity);

    default T insert(T t) {
        return getJdbcAggregateTemplate().insert(t);
    }

    default T update(T t) {
        return getJdbcAggregateTemplate().update(t);
    }
}