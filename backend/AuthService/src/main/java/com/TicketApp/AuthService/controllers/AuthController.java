package com.TicketApp.AuthService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.TicketApp.AuthService.requestVO.TokenIntrospectionRequest;
import com.TicketApp.AuthService.requestVO.UserLoginRequest;
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
    public TokenIntrospectionResponse postMethodName(@RequestBody TokenIntrospectionRequest tokenRequest) {
        return authService.introspect(tokenRequest.getToken());
    }
    
}
