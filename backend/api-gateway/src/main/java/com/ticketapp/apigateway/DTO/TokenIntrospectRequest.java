package com.ticketapp.apigateway.DTO;

public class TokenIntrospectRequest {
    private String token;

    public String getToken(){
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }

}
