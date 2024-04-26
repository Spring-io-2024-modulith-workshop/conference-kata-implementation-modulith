package com.acme.conferencesystem.ticket.business;

import com.acme.conferencesystem.ticket.persistence.TicketEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketEntity ticketEntity(Ticket ticket);

    Ticket ticket(TicketEntity ticketEntity);
}
