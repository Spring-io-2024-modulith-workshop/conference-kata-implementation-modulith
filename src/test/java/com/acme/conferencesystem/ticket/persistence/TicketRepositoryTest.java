package com.acme.conferencesystem.ticket.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.acme.conferencesystem.ContainerConfig;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest
@Import(ContainerConfig.class)
class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;

    @Test
    void save() {
        var entity = Instancio.of(TicketEntity.class)
                .ignore(Select.field(TicketEntity::id))
                .create();

        TicketEntity saved = ticketRepository.save(entity);

        assertThat(saved)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(entity);
    }
}
