package com.acme.conferencesystem.ticket.http;

import com.acme.conferencesystem.ticket.business.Ticket;
import com.acme.conferencesystem.ticket.business.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PutMapping("/buy")
    public ResponseEntity<Ticket> buyTicket(Ticket ticket) {
        return ResponseEntity.status(201).body(ticketService.buyTicket(ticket));
    }

    @PutMapping("/book")
    public ResponseEntity<Ticket> bookTicket(Ticket ticket) {
        return ResponseEntity.status(201).body(ticketService.bookTicket(ticket));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<Void> cancelTicket(Ticket ticket) {
        ticketService.cancelTicket(ticket);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/confirm")
    public ResponseEntity<Void> confirmTicket(Ticket ticket) {
        ticketService.confirmTicket(ticket);
        return ResponseEntity.noContent().build();
    }
}
