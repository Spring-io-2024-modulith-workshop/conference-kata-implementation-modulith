package com.acme.conferencesystem.ticket.http;

import com.acme.conferencesystem.ticket.business.Ticket;
import com.acme.conferencesystem.ticket.business.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
class TicketController {

    private final TicketService ticketService;

    TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    ResponseEntity<Ticket> getTicketById(@PathVariable UUID id) {
        return ticketService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/buy")
    ResponseEntity<Ticket> buyTicket(@Valid @RequestBody Ticket ticket) {
        return ResponseEntity.status(201).body(ticketService.buyTicket(ticket));
    }

    @PostMapping("/book")
    ResponseEntity<Ticket> bookTicket(@Valid @RequestBody Ticket ticket) {
        return ResponseEntity.status(201).body(ticketService.bookTicket(ticket));
    }

    @PatchMapping("/{id}/cancel")
    ResponseEntity<Void> cancelTicket(@PathVariable UUID id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/confirm")
    ResponseEntity<Void> confirmTicket(@PathVariable UUID id) {
        ticketService.confirmTicket(id);
        return ResponseEntity.ok().build();
    }
}
