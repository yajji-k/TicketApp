package com.TicketApp.AuthService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TicketApp.AuthService.entities.AuthToken;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findByTokenAndRevokedFalse(String token);
}
