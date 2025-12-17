package com.TicketApp.AuthService.requestVO;

public class TokenIntrospectionRequest {

    private String token;

    public String getToken(){
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
