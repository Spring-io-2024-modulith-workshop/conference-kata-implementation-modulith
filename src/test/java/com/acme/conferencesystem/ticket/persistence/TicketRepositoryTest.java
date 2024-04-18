package com.acme.conferencesystem.ticket.persistence;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
@Import(ContainerConfig.class)
class TicketRepositoryTest extends AbstractIntegrationTest {

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
