package com.acme.conferencesystem.ticket.business;

import com.acme.conferencesystem.ticket.persistence.TicketEntity;
import com.acme.conferencesystem.ticket.persistence.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static com.acme.conferencesystem.ticket.business.TicketCategory.CANCELLED;
import static com.acme.conferencesystem.ticket.business.TicketCategory.CONFIRMED;
import static com.acme.conferencesystem.ticket.business.TicketCategory.RESERVED;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
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
        TicketEntity ticketEntity = new TicketEntity(ticket.id(), CONFIRMED, ticket.date());
        ticketRepository.save(ticketEntity);
        return ticketMapper.ticket(ticketEntity);
    }

    public Ticket bookTicket(Ticket ticket) {
        TicketEntity ticketEntity = new TicketEntity(ticket.id(), RESERVED, ticket.date());
        ticketRepository.save(ticketEntity);
        return ticketMapper.ticket(ticketEntity);
    }

    public void cancelTicket(Ticket ticket) {
        ticketRepository.findById(ticket.id())
                .ifPresent(ticketEntity -> {
                    var cancelledTicket = new TicketEntity(ticketEntity.id(), CANCELLED, ticketEntity.date());
                    ticketRepository.save(cancelledTicket);
                });
    }

    public void confirmTicket(Ticket ticket) {
        ticketRepository.findById(ticket.id())
                .ifPresent(ticketEntity -> {
                    var cancelledTicket = new TicketEntity(ticketEntity.id(), CONFIRMED, ticketEntity.date());
                    ticketRepository.save(cancelledTicket);
                });
    }

    public Optional<Ticket> getById(UUID id) {
        return ticketRepository.findById(id).map(ticketMapper::ticket);
    }
}
