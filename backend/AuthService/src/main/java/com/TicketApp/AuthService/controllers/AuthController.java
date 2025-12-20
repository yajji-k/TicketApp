package com.TicketApp.AuthService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.TicketApp.AuthService.exception.InvalidTokenException;
import com.TicketApp.AuthService.requestVO.TokenIntrospectionRequest;
import com.TicketApp.AuthService.requestVO.UserLoginRequest;
import com.TicketApp.AuthService.responseVO.RefreshTokenResponse;
import com.TicketApp.AuthService.responseVO.TokenIntrospectionResponse;
import com.TicketApp.AuthService.responseVO.UserLoginResponse;
import com.TicketApp.AuthService.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public UserLoginResponse postLogin(@RequestBody UserLoginRequest user) {
        return authService.UserLoginService(user);
    }

    @PostMapping("/introspect")
    public TokenIntrospectionResponse introspectToken(@RequestBody TokenIntrospectionRequest tokenRequest) {
        return authService.introspect(tokenRequest.getToken());
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Missing Authorization header");
        }

        String token = authHeader.substring(7);
        return authService.refresh(token);
    }

}
