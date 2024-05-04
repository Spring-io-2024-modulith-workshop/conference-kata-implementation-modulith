package com.acme.conferencesystem.voting.persistence;

import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(ContainerVoteTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class VoteRepositoryTest {

    public static final String SAMPLE_PROPOSAL = "2da9d8c5-b9ab-43b7-bb43-5b82ca8754d4";
    public static final String SAMPLE_USER = "5768d8a7-bdb0-4022-9fb0-fa7c68491287";
    @Autowired
    VoteRepository voteRepository;

    @Test
    @Transactional
    void save() {

        var entity = Instancio.of(VoteEntity.class)
                .ignore(Select.field(VoteEntity::id))
                .set(Select.field(VoteEntity::proposalId), SAMPLE_PROPOSAL)
                .set(Select.field(VoteEntity::userId), SAMPLE_USER)
                .create();

        var persisted = voteRepository.save(entity);

        Assertions.assertThat(persisted.id()).isNotNull();
    }
}
