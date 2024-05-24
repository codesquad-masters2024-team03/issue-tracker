package com.codesquad.team3.issuetracker.domain.jwt.repository;

import com.codesquad.team3.issuetracker.domain.jwt.entity.RefreshToken;
import com.codesquad.team3.issuetracker.support.repository.SimpleCrudRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends SimpleCrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
}
