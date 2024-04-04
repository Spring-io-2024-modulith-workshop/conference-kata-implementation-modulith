package com.acme.conferencesystem.ticket.business;

import java.util.UUID;

public record Ticket(UUID id, TicketCategory category) {
}
