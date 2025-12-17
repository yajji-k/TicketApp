package com.TicketApp.AuthService.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(
            InvalidTokenException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ActiveSessionExistsException.class)
    public ResponseEntity<Map<String, Object>> handleActiveSession(
            ActiveSessionExistsException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("error", "ACTIVE_SESSION_EXISTS");
        body.put("message", ex.getMessage());
        body.put("timestamp", Instant.now());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
}
