package com.TicketApp.AuthService.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/demo")
public class HelloController {

    @GetMapping()
    public String getMethodName() {
        return new String("Controller is working");
    }

    
    
}
