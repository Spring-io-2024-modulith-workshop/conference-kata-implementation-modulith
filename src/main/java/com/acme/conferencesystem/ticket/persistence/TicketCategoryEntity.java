package com.acme.conferencesystem.ticket.persistence;

import org.springframework.data.relational.core.mapping.Table;

@Table("ticket_category")
public enum TicketCategoryEntity {
    CANCELLED, RESERVED, PENDING, CONFIRMED
}
