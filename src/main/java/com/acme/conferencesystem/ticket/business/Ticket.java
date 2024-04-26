package com.acme.conferencesystem.ticket.business;

import java.time.LocalDateTime;
import java.util.UUID;

public record Ticket(UUID id,
                     TicketCategory category,
                     LocalDateTime date,
                     Double price,
                     TicketStatus status) {
}
