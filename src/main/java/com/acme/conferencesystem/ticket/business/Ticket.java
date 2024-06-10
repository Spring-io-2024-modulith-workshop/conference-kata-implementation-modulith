package com.acme.conferencesystem.ticket.business;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public record Ticket(@Identity UUID id,
                     TicketCategory category,
                     LocalDateTime date,
                     Double price,
                     TicketStatus status) {
}
