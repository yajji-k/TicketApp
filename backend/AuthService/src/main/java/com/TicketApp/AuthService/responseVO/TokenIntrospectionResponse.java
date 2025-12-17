package com.TicketApp.AuthService.responseVO;

import java.time.Instant;
import java.util.Set;

public class TokenIntrospectionResponse {

    private boolean active;
    private String username;
    private Instant expiresAt;
    private Set<String> roles;

    public TokenIntrospectionResponse() {}

    public TokenIntrospectionResponse(boolean active) {
        this.active = active;
    }

    public TokenIntrospectionResponse(boolean active, String username, Instant expiresAt, Set<String> roles) {
        this.active = active;
        this.username = username;
        this.expiresAt = expiresAt;
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public String getUsername() {
        return username;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
