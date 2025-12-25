package com.ticketapp.apigateway.DTO;

import java.time.Instant;
import java.util.Set;

public class TokenIntrospectResponse {
    private boolean active;
    private String username;
    private Instant expiresAt;
    private Set<String> roles;

    public boolean isActive(){
        return this.active;
    }

    public String getUsername(){
        return this.username;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    
}
