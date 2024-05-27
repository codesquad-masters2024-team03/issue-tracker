package com.codesquad.team3.issuetracker.domain.member.repository;

import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import com.codesquad.team3.issuetracker.support.repository.SoftDeleteCrudRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends SoftDeleteCrudRepository<Member, Integer> {
    @Override
    default Class<Member> getType(){
        return Member.class;
    }

    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByRefreshToken(String refreshToken);
}