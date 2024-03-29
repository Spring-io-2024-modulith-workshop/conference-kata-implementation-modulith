package com.acme.conferencesystem.cfp.proposals.persistence;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@DataJdbcTest
public class ProposalRepositoryTest {

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
