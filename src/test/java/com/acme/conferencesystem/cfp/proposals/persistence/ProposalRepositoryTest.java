package com.acme.conferencesystem.cfp.proposals.persistence;

import com.acme.conferencesystem.AbstractIntegrationTest;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class ProposalRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    ProposalRepository repository;

    @Test
    void save() {
        var entity = Instancio.of(ProposalEntity.class).ignore(field(ProposalEntity::id)).create();

        ProposalEntity persisted = repository.save(entity);

        assertThat(persisted.id()).isNotNull();
        assertThat(persisted)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(entity);
    }
}
