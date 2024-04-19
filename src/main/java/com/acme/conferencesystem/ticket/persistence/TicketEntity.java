package com.acme.conferencesystem.ticket.persistence;

import com.acme.conferencesystem.ticket.business.TicketCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("tickets")
public record TicketEntity(@Id UUID id,
                           TicketCategory category,
                           LocalDate date) {
}