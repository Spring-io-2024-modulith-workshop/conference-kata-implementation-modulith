package com.acme.conferencesystem.ticket.business;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record TicketCategory(@Id UUID id, String name) {
}
