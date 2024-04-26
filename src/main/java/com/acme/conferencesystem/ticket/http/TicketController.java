package com.acme.conferencesystem.ticket.http;

import com.acme.conferencesystem.ticket.business.Ticket;
import com.acme.conferencesystem.ticket.business.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable UUID id) {
        return ticketService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/buy")
    public ResponseEntity<Ticket> buyTicket(@Valid @RequestBody Ticket ticket) {
        return ResponseEntity.status(201).body(ticketService.buyTicket(ticket));
    }

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody Ticket ticket) {
        return ResponseEntity.status(201).body(ticketService.bookTicket(ticket));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelTicket(@PathVariable UUID id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmTicket(@PathVariable UUID id) {
        ticketService.confirmTicket(id);
        return ResponseEntity.ok().build();
    }
}
