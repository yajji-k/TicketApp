package com.TicketApp.AuthService.responseVO;

import java.time.Instant;
import java.util.Set;

public class UserLoginResponse {

    private String accessToken;
    private String tokenType;
    private Instant expiresAt;
    private Set<String> roles;

    public UserLoginResponse() {
    }

    public UserLoginResponse(String accessToken, Instant expiresAt, Set<String> roles) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.expiresAt = expiresAt;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setRoles(Set<String> roles){
        this.roles = roles;
    }

    public Set<String> getRoles(){
        return this.roles;
    }
}
