package com.codesquad.team3.issuetracker.support.repository;

import com.codesquad.team3.issuetracker.support.ApplicationContextProvider;
import com.codesquad.team3.issuetracker.support.enums.OpenCloseSearchFlags;
import com.codesquad.team3.issuetracker.support.enums.SoftDeleteSearchFlags;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

public class RepositorySupport {

    private RepositorySupport() {
    }

    public static Query getQuery(SoftDeleteSearchFlags softFlags, OpenCloseSearchFlags closeFlags) {
        Criteria criteria = Criteria.empty();
        if (softFlags.equals(SoftDeleteSearchFlags.ALL) && closeFlags.equals(OpenCloseSearchFlags.ALL)) {
            return Query.query(criteria);
        }
        if (softFlags.equals(SoftDeleteSearchFlags.ALL)) {
            return getQuery(closeFlags);
        }

        if(closeFlags.equals(OpenCloseSearchFlags.ALL)) {
            return getQuery(softFlags);
        }

        boolean searchCondition1 = softFlags.equals(SoftDeleteSearchFlags.DELETED);
        boolean searchCondition2 = closeFlags.equals(OpenCloseSearchFlags.CLOSE);

        criteria = Criteria.where("is_deleted").is(searchCondition1).and("is_closed").is(searchCondition2);

        return Query.query(criteria);
    }

    public static Query getQuery(SoftDeleteSearchFlags flags) {
        Criteria criteria = Criteria.empty();
        if (!flags.equals(SoftDeleteSearchFlags.ALL)) {
            boolean searchCondition = flags.equals(SoftDeleteSearchFlags.DELETED);
            criteria = Criteria.where("is_deleted").is(searchCondition);
        }

        return Query.query(criteria);
    }

    public static <ID> Query getQuery(ID id, SoftDeleteSearchFlags flags) {
        Criteria criteria;
        if (!flags.equals(SoftDeleteSearchFlags.ALL)) {
            boolean searchCondition = flags.equals(SoftDeleteSearchFlags.DELETED);
            criteria = Criteria.where("is_deleted").is(searchCondition).and("id").is(id);
        } else {
            criteria = Criteria.where("id").is(id);
        }

        return Query.query(criteria);
    }

    public static Query getQuery(OpenCloseSearchFlags flags) {
        Criteria criteria = Criteria.empty();
        if (!flags.equals(OpenCloseSearchFlags.ALL)) {
            boolean searchCondition = flags.equals(OpenCloseSearchFlags.CLOSE);
            criteria = Criteria.where("is_closed").is(searchCondition);
        }

        return Query.query(criteria);
    }

    public static <ID> Query getQuery(ID id, OpenCloseSearchFlags flags) {
        Criteria criteria;
        if (!flags.equals(OpenCloseSearchFlags.ALL)) {
            boolean searchCondition = flags.equals(OpenCloseSearchFlags.CLOSE);
            criteria = Criteria.where("is_closed").is(searchCondition).and("id").is(id);
        } else {
            criteria = Criteria.where("id").is(id);
        }

        return Query.query(criteria);
    }

    public static JdbcAggregateTemplate getJdbcAggregateTemplate() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        return ctx.getBean(JdbcAggregateTemplate.class);
    }
}
