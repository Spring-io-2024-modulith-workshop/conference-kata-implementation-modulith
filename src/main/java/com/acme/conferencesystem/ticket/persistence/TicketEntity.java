package com.acme.conferencesystem.ticket.persistence;

import com.acme.conferencesystem.ticket.business.TicketCategory;
import com.acme.conferencesystem.ticket.business.TicketStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Table("tickets")
public record TicketEntity(@Id UUID id,
                           TicketCategory category,
                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssX")
                           LocalDate date,
                           Double price,
                           TicketStatus status) {
}
