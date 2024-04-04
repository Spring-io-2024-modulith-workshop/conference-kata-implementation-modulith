package com.acme.conferencesystem.ticket.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("ticket_category")
public record TicketCategoryEntity(@Id UUID id, String name) {
}
