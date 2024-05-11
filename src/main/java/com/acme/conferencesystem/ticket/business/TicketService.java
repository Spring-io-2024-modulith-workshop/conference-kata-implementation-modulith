package com.acme.conferencesystem.ticket.business;

import static com.acme.conferencesystem.ticket.business.TicketStatus.CONFIRMED;

import com.acme.conferencesystem.ticket.persistence.TicketEntity;
import com.acme.conferencesystem.ticket.persistence.TicketRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<Ticket> getAllTickets() {
        return StreamSupport
                .stream(ticketRepository.findAll().spliterator(), false)
                .map(ticketMapper::ticket)
                .toList();
    }

    public Ticket buyTicket(Ticket ticket) {
        TicketEntity ticketEntity = new TicketEntity(null, ticket.category(), ticket.date(), ticket.price(), CONFIRMED);
        TicketEntity persisted = ticketRepository.save(ticketEntity);
        return ticketMapper.ticket(persisted);
    }

    public Ticket bookTicket(Ticket ticket) {
        TicketEntity ticketEntity = TicketEntity.createWithReservedStatus(ticket.category(), ticket.date(), ticket.price());
        TicketEntity persisted = ticketRepository.save(ticketEntity);
        return ticketMapper.ticket(persisted);
    }

    public void cancelTicket(UUID ticket) {
        ticketRepository
                .findById(ticket)
                .map(TicketEntity::createWithCancelledStatus)
                .ifPresent(ticketRepository::save);
    }

    public void confirmTicket(UUID ticket) {
        ticketRepository
                .findById(ticket)
                .map(TicketEntity::createWithConfirmedStatus)
                .ifPresent(ticketRepository::save);
    }

    public Optional<Ticket> getById(UUID id) {
        return ticketRepository.findById(id).map(ticketMapper::ticket);
    }
}
