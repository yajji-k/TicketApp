package com.ticketapp.ticketservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @PostMapping("/test")
    public ResponseEntity<String> receiveUserId(@RequestHeader("X-User-Id") String username) {
        try {
            logger.info("User Name received from API Gateway: {}", username);
            return ResponseEntity.ok("User ID processed successfully");
        } catch (Exception ex) {
            logger.error("Error while processing request from API Gateway", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process request");
        }
    }
}
