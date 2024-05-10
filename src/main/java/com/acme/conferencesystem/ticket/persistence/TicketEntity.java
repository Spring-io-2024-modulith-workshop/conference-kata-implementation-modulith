package com.acme.conferencesystem.ticket.persistence;

import com.acme.conferencesystem.ticket.business.TicketCategory;
import com.acme.conferencesystem.ticket.business.TicketStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.acme.conferencesystem.ticket.business.TicketStatus.CANCELLED;
import static com.acme.conferencesystem.ticket.business.TicketStatus.CONFIRMED;
import static com.acme.conferencesystem.ticket.business.TicketStatus.RESERVED;

@Table("tickets")
public record TicketEntity(@Id UUID id,
                           TicketCategory category,
                           @CreatedDate
                           LocalDateTime date,
                           Double price,
                           TicketStatus status) {

    public static TicketEntity createWithReservedStatus(TicketCategory category, LocalDateTime date, Double price) {

        return new TicketEntity(null, category, date, price, RESERVED);
    }

    public TicketEntity createWithConfirmedStatus() {
        return new TicketEntity(id, category, date, price, CONFIRMED);
    }

    public TicketEntity createWithCancelledStatus() {

        return new TicketEntity(id, category, date, price, CANCELLED);
    }
}
