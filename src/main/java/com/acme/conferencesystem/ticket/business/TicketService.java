package com.acme.conferencesystem.ticket.business;

import com.acme.conferencesystem.ticket.persistence.TicketEntity;
import com.acme.conferencesystem.ticket.persistence.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static com.acme.conferencesystem.ticket.business.TicketStatus.CANCELLED;
import static com.acme.conferencesystem.ticket.business.TicketStatus.CONFIRMED;
import static com.acme.conferencesystem.ticket.business.TicketStatus.RESERVED;

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
        TicketEntity ticketEntity = new TicketEntity(null, ticket.category(), ticket.date(), ticket.price(), CONFIRMED);
        TicketEntity persisted = ticketRepository.save(ticketEntity);
        return ticketMapper.ticket(persisted);
    }

    public Ticket bookTicket(Ticket ticket) {
        TicketEntity ticketEntity = new TicketEntity(null, ticket.category(), ticket.date(), ticket.price(), RESERVED);
        TicketEntity persisted = ticketRepository.save(ticketEntity);
        return ticketMapper.ticket(persisted);
    }

    public void cancelTicket(UUID ticket) {
        ticketRepository.findById(ticket)
                .ifPresent(ticketEntity -> {
                    var cancelledTicket = new TicketEntity(ticketEntity.id(), ticketEntity.category(), LocalDateTime.now(), ticketEntity.price(), CANCELLED);
                    ticketRepository.save(cancelledTicket);
                });
    }

    public void confirmTicket(UUID ticket) {
        ticketRepository.findById(ticket)
                .ifPresent(ticketEntity -> {
                    var cancelledTicket = new TicketEntity(ticketEntity.id(), ticketEntity.category(), LocalDateTime.now(), ticketEntity.price(), CONFIRMED);
                    ticketRepository.save(cancelledTicket);
                });
    }

    public Optional<Ticket> getById(UUID id) {
        return ticketRepository.findById(id).map(ticketMapper::ticket);
    }
}
