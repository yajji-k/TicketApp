package com.ticketapp.ticketservice.service;

import org.springframework.stereotype.Service;

import com.ticketapp.ticketservice.DTO.CreateTicketRequest;
import com.ticketapp.ticketservice.DTO.TicketResponse;
import com.ticketapp.ticketservice.entity.Ticket;
import com.ticketapp.ticketservice.entity.TicketStatus;
import com.ticketapp.ticketservice.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketResponse createTicket(String userId, CreateTicketRequest request) {

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedBy(userId);

        Ticket saved = ticketRepository.save(ticket);

        return mapToResponse(saved);
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus().name());
        response.setCreatedBy(ticket.getCreatedBy());
        response.setCreatedAt(ticket.getCreatedAt());
        return response;
    }
}

