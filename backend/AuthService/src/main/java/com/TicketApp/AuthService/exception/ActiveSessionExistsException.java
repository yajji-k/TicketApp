package com.TicketApp.AuthService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409
public class ActiveSessionExistsException extends RuntimeException {
    public ActiveSessionExistsException(String message) {
        super(message);
    }
}
