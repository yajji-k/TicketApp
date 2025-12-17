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
import com.TicketApp.AuthService.exception.InvalidCredentialsException;
import com.TicketApp.AuthService.repositories.AuthTokenRepository;
import com.TicketApp.AuthService.repositories.UserRepository;
import com.TicketApp.AuthService.requestVO.UserLoginRequest;
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

        Optional<AuthToken> tokenOpt =
                authTokenRepository.findByTokenAndRevokedFalse(tokenValue);

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
                roles
        );
    }


}
