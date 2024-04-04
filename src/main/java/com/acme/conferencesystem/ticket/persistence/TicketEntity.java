package com.acme.conferencesystem.ticket.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("ticket")
public record TicketEntity(@Id UUID id, TicketCategoryEntity category) {
}
