package com.TicketApp.AuthService.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TicketApp.AuthService.entities.AuthToken;
import com.TicketApp.AuthService.entities.User;
import com.TicketApp.AuthService.exception.ActiveSessionExistsException;
import com.TicketApp.AuthService.exception.InvalidCredentialsException;
import com.TicketApp.AuthService.exception.InvalidTokenException;
import com.TicketApp.AuthService.repositories.AuthTokenRepository;
import com.TicketApp.AuthService.repositories.UserRepository;
import com.TicketApp.AuthService.requestVO.RefreshTokenRequest;
import com.TicketApp.AuthService.requestVO.UserLoginRequest;
import com.TicketApp.AuthService.responseVO.RefreshTokenResponse;
import com.TicketApp.AuthService.responseVO.TokenIntrospectionResponse;
import com.TicketApp.AuthService.responseVO.UserLoginResponse;

@Service
public class AuthService {

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Autowired
    UserRepository userRepository;

    public UserLoginResponse UserLoginService(UserLoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        if (!request.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        boolean hasActiveSession = authTokenRepository.existsByUsernameAndRevokedFalse(user.getUsername());

        if (hasActiveSession && !request.getForceLogin()) {
            throw new ActiveSessionExistsException(
                    "User already logged in on another device");
        }

        if (request.getForceLogin()) {
            authTokenRepository.revokeAllActiveTokensForUser(user.getUsername());
        }

        String tokenValue = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now().plus(30, ChronoUnit.MINUTES);

        AuthToken token = new AuthToken();
        token.setToken(tokenValue);
        token.setUsername(user.getUsername());
        token.setExpiresAt(expiresAt);
        token.setRevoked(false);

        authTokenRepository.save(token);

        Set<String> userRole = user.getRoleSet();

        return new UserLoginResponse(tokenValue, expiresAt, userRole);
    }

    public TokenIntrospectionResponse introspect(String tokenValue) {

        Optional<AuthToken> tokenOpt = authTokenRepository.findByTokenAndRevokedFalse(tokenValue);

        if (tokenOpt.isEmpty()) {
            return new TokenIntrospectionResponse(false);
        }

        AuthToken token = tokenOpt.get();

        if (token.getExpiresAt().isBefore(Instant.now())) {
            return new TokenIntrospectionResponse(false);
        }

        User user = userRepository.findByUsername(token.getUsername())
                .orElseThrow();

        Set<String> roles = user.getRoleSet();

        return new TokenIntrospectionResponse(
                true,
                user.getUsername(),
                token.getExpiresAt(),
                roles);
    }

    public RefreshTokenResponse refresh(String tokenValue) {

        // 1. Find active token
        AuthToken oldToken = authTokenRepository
                .findByTokenAndRevokedFalse(tokenValue)
                .orElseThrow(() -> new InvalidTokenException("Invalid or revoked token"));

        // 2. Check expiry
        if (oldToken.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidTokenException("Token expired");
        }

        // 3. Revoke old token (rotation)
        oldToken.setRevoked(true);
        authTokenRepository.save(oldToken);

        // 4. Generate new token
        String newTokenValue = UUID.randomUUID().toString();
        long expiresInSeconds = 30 * 60; // 30 minutes
        Instant newExpiresAt = Instant.now().plusSeconds(expiresInSeconds);

        AuthToken newToken = new AuthToken();
        newToken.setToken(newTokenValue);
        newToken.setUsername(oldToken.getUsername());
        newToken.setExpiresAt(newExpiresAt);
        newToken.setRevoked(false);

        authTokenRepository.save(newToken);

        // 5. Return response
        return new RefreshTokenResponse(
                newTokenValue,
                expiresInSeconds);
    }
}
