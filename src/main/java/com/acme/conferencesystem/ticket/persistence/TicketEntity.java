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

@Table("tickets")
public record TicketEntity(@Id UUID id,
                           TicketCategory category,
                           @CreatedDate
                           LocalDateTime date,
                           Double price,
                           TicketStatus status) {

    public TicketEntity ofConfirmed() {
        return new TicketEntity(id, category, date, price, CONFIRMED);
    }

    public TicketEntity ofCancelled() {
        return new TicketEntity(id, category, date, price, CANCELLED);
    }
}
