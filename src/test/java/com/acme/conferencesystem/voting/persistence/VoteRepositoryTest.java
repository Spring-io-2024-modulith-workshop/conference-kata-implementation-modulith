package com.acme.conferencesystem.voting.persistence;

import com.acme.conferencesystem.ContainerConfig;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(ContainerConfig.class)
class VoteRepositoryTest {

    @Autowired
    VoteRepository voteRepository;

    @Test
    @Transactional
    void save() {
        var entity = Instancio.of(VoteEntity.class)
                .ignore(Select.field(VoteEntity::id))
                .set(Select.field(VoteEntity::proposalId), UUID.randomUUID())
                .set(Select.field(VoteEntity::userId), UUID.randomUUID())
                .create();

        var persisted = voteRepository.save(entity);

        Assertions.assertThat(persisted.id()).isNotNull();
    }
}
