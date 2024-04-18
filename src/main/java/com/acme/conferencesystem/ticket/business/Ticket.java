package com.acme.conferencesystem.ticket.business;

import java.time.LocalDate;
import java.util.UUID;

public record Ticket(UUID id, TicketCategory category, LocalDate date) {
}
