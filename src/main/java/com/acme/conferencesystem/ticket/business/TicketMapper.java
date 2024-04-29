package com.acme.conferencesystem.ticket.business;

import com.acme.conferencesystem.ticket.persistence.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

    TicketEntity ticketEntity(Ticket ticket);

    Ticket ticket(TicketEntity ticketEntity);
}
