package com.TicketApp.AuthService.responseVO;

public class RefreshTokenResponse {

    private String accessToken;
    private String tokenType;
    private long expiresIn; // seconds

    public RefreshTokenResponse() {
    }

    public RefreshTokenResponse(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
