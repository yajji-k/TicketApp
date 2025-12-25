package com.ticketapp.ticketservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketapp.ticketservice.DTO.CreateTicketRequest;
import com.ticketapp.ticketservice.DTO.TicketResponse;
import com.ticketapp.ticketservice.service.TicketService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createTicket(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreateTicketRequest request) {

        TicketResponse response = ticketService.createTicket(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
