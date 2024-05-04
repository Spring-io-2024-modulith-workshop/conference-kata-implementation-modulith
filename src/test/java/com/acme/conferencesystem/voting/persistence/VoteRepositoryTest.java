package com.acme.conferencesystem.voting.persistence;

import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.transaction.annotation.Transactional;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(ContainerVoteTestConfig.class)
class VoteRepositoryTest {

    public static final String SAMPLE_PROPOSAL = "43736d30-28c0-4e2c-9c1d-f8e37c929f0e";
    public static final String SAMPLE_USER = "51d205e8-d274-4923-9623-c4f23aea0ce5";
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
