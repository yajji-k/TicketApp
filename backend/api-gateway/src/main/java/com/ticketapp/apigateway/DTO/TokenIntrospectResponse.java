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
}
