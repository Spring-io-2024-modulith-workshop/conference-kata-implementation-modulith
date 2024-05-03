package com.acme.conferencesystem.voting.persistence;

import com.acme.conferencesystem.ContainerConfig;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.instancio.Select.field;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(ContainerConfig.class)
class VoteRepositoryTest {

    private static final UUID dummyUUID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Autowired
    VoteRepository voteRepository;

    @Test
    @Transactional
    void save() {
        var entity = Instancio.of(VoteEntity.class)
                .ignore(field(VoteEntity::id))
                .set(field(VoteEntity::proposalId), dummyUUID)
                .set(field(VoteEntity::userId), dummyUUID)
                .create();

        var persisted = voteRepository.save(entity);

        Assertions.assertThat(persisted.id()).isNotNull();
    }
}
