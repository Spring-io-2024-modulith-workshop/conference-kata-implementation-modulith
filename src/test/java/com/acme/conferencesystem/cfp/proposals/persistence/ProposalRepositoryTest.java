package com.acme.conferencesystem.cfp.proposals.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

import com.acme.conferencesystem.ContainerConfig;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Import(ContainerConfig.class)
class ProposalRepositoryTest {

    @Autowired
    ProposalRepository repository;

    @Test
    @Transactional
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
