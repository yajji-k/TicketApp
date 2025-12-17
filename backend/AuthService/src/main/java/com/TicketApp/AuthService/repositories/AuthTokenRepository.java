package com.TicketApp.AuthService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.TicketApp.AuthService.entities.AuthToken;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findByTokenAndRevokedFalse(String token);

    boolean existsByUsernameAndRevokedFalse(String username);

    @Modifying
    @Transactional
    @Query("""
        UPDATE AuthToken t
        SET t.revoked = true
        WHERE t.username = :username
          AND t.revoked = false
    """)
    int revokeAllActiveTokensForUser(@Param("username") String username);
}
