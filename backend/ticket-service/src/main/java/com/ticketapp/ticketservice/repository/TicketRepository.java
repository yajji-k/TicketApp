package com.ticketapp.ticketservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketapp.ticketservice.entity.Ticket;
import com.ticketapp.ticketservice.entity.TicketStatus;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedBy(String createdBy);

    List<Ticket> findByCreatedByAndStatus(String createdBy, TicketStatus status);
}
