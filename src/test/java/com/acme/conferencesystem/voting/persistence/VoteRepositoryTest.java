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

@ApplicationModuleTest
@Import(ContainerConfig.class)
class VoteRepositoryTest {

    @Autowired
    VoteRepository voteRepository;

    @Test
    @Transactional
    void save() {
        var entity = Instancio.of(VoteEntity.class)
                .ignore(Select.field(VoteEntity::id))
                .create();

        var persisted = voteRepository.save(entity);

        Assertions.assertThat(persisted.id()).isNotNull();
    }
}
