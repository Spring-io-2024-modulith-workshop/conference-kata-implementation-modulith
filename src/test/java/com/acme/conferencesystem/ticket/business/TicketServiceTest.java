package com.acme.conferencesystem.ticket.business;

import com.acme.conferencesystem.ticket.persistence.TicketEntity;
import com.acme.conferencesystem.ticket.persistence.TicketRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.acme.conferencesystem.ticket.business.TicketStatus.CONFIRMED;
import static com.acme.conferencesystem.ticket.business.TicketStatus.RESERVED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertThat(allTickets).hasSize(3);
    }

    @Test
    void buyTicket() {

        var ticketToBuy = Instancio.of(Ticket.class).create();
        var ticketEntity = Instancio.of(TicketEntity.class)
                .set(field("status"), CONFIRMED)
                .set(field("category"), ticketToBuy.category())
                .set(field("id"), ticketToBuy.id())
                .set(field("price"), ticketToBuy.price())
                .set(field("date"), ticketToBuy.date())
                .create();

        when(ticketRepository.save(any(TicketEntity.class)))
                .thenReturn(ticketEntity);

        var ticket = ticketService.buyTicket(ticketToBuy);

        verify(ticketMapper, atMostOnce()).ticketEntity(ticketToBuy);

        assertThat(ticket.status()).isEqualTo(CONFIRMED);
        assertThat(ticket).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("status")
                .isEqualTo(ticketToBuy);

    }

    @Test
    void bookTicket() {
        Ticket ticketToBook = Instancio.of(Ticket.class).create();
        var ticketEntity = Instancio.of(TicketEntity.class)
                .set(field("status"), RESERVED)
                .set(field("category"), ticketToBook.category())
                .set(field("id"), ticketToBook.id())
                .set(field("price"), ticketToBook.price())
                .set(field("date"), ticketToBook.date())
                .create();

        when(ticketRepository.save(any(TicketEntity.class)))
                .thenReturn(ticketEntity);

        Ticket ticket = ticketService.bookTicket(ticketToBook);
        assertThat(ticket.status()).isEqualTo(RESERVED);
        assertThat(ticket).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("status")
                .isEqualTo(ticketToBook);
    }

    @Test
    void cancelTicket() {
        Ticket ticketToCancel = Instancio.of(Ticket.class).create();
        TicketEntity ticketEntity = ticketMapper.ticketEntity(ticketToCancel);
        given(ticketRepository.save(any(TicketEntity.class))).willReturn(ticketEntity);
        given(ticketRepository.findById(ticketToCancel.id())).willReturn(Optional.of(ticketEntity));

        ticketService.bookTicket(ticketToCancel);
        ticketService.cancelTicket(ticketToCancel.id());
        Optional<Ticket> byId = ticketService.getById(ticketToCancel.id());

        assertThat(byId).isNotEmpty();
        verify(ticketRepository, atMost(2)).save(any(TicketEntity.class));
    }

    @Test
    void confirmTicket() {
        Ticket ticketToConfirm = Instancio.of(Ticket.class).create();
        TicketEntity ticketEntity = ticketMapper.ticketEntity(ticketToConfirm);
        given(ticketRepository.save(any(TicketEntity.class))).willReturn(ticketEntity);
        given(ticketRepository.findById(ticketToConfirm.id())).willReturn(Optional.of(ticketEntity));

        ticketService.bookTicket(ticketToConfirm);
        ticketService.confirmTicket(ticketToConfirm.id());
        Optional<Ticket> byId = ticketService.getById(ticketToConfirm.id());

        assertThat(byId).isNotEmpty();
    }
}