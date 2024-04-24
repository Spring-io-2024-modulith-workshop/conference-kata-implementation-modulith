package com.acme.conferencesystem.ticket.business;

import com.acme.conferencesystem.ticket.persistence.TicketEntity;
import com.acme.conferencesystem.ticket.persistence.TicketRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.acme.conferencesystem.ticket.business.TicketStatus.CONFIRMED;
import static com.acme.conferencesystem.ticket.business.TicketStatus.RESERVED;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketRepository ticketRepository;

    @Spy
    TicketMapper ticketMapper = new TicketMapperImpl();

    @Test
    void getAllTickets() {
        List<TicketEntity> ticketList = Instancio.ofList(TicketEntity.class).size(3).create();
        given(ticketRepository.findAll()).willReturn(ticketList);

        List<Ticket> allTickets = ticketService.getAllTickets();
        Assertions.assertThat(allTickets).hasSize(3);
    }

    @Test
    void buyTicket() {

        Ticket ticketToBuy = Instancio.of(Ticket.class).create();
        Ticket ticket = ticketService.buyTicket(ticketToBuy);
        Assertions.assertThat(ticket.status()).isEqualTo(CONFIRMED);
        Assertions.assertThat(ticket).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("status")
                .isEqualTo(ticketToBuy);

    }

    @Test
    void bookTicket() {
        Ticket ticketToBook = Instancio.of(Ticket.class).create();
        Ticket ticket = ticketService.bookTicket(ticketToBook);
        Assertions.assertThat(ticket.status()).isEqualTo(RESERVED);
        Assertions.assertThat(ticket).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("status")
                .isEqualTo(ticketToBook);
    }

    @Test
    void cancelTicket() {
        Ticket ticketToCancel = Instancio.of(Ticket.class).create();
        TicketEntity ticketEntity = ticketMapper.ticketEntity(ticketToCancel);
        given(ticketRepository.save(Mockito.any(TicketEntity.class))).willReturn(ticketEntity);
        given(ticketRepository.findById(ticketToCancel.id())).willReturn(Optional.of(ticketEntity));

        ticketService.bookTicket(ticketToCancel);
        ticketService.cancelTicket(ticketToCancel.id());
        Optional<Ticket> byId = ticketService.getById(ticketToCancel.id());

        Assertions.assertThat(byId).isNotEmpty();
    }

    @Test
    void confirmTicket() {
        Ticket ticketToConfirm = Instancio.of(Ticket.class).create();
        TicketEntity ticketEntity = ticketMapper.ticketEntity(ticketToConfirm);
        given(ticketRepository.save(Mockito.any(TicketEntity.class))).willReturn(ticketEntity);
        given(ticketRepository.findById(ticketToConfirm.id())).willReturn(Optional.of(ticketEntity));

        ticketService.bookTicket(ticketToConfirm);
        ticketService.confirmTicket(ticketToConfirm.id());
        Optional<Ticket> byId = ticketService.getById(ticketToConfirm.id());

        Assertions.assertThat(byId).isNotEmpty();
    }
}