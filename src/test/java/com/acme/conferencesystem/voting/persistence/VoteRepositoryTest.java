package com.acme.conferencesystem.voting.persistence;

import static org.instancio.Select.field;

import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.persistence.UserEntity;
import com.acme.conferencesystem.users.persistence.UsersRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.transaction.annotation.Transactional;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(ContainerConfig.class)
class VoteRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    VoteRepository voteRepository;
    private ProposalEntity persistedProposal;
    private UserEntity persistedUser;

    @BeforeEach
    void setUp() {

        persistedUser = usersRepository.save(
                Instancio.of(UserEntity.class)
                        .ignore(field(UserEntity::id))
                        .create());

        persistedProposal = proposalRepository.save(
                Instancio.of(ProposalEntity.class)
                        .ignore(field(ProposalEntity::id))
                        .set(field(ProposalEntity::speakerId), persistedUser.id())
                        .create());
    }

    @Test
    @Transactional
    void save() {

        var entity = Instancio.of(VoteEntity.class)
                .ignore(field(VoteEntity::id))
                .set(field(VoteEntity::proposalId), persistedProposal.id())
                .set(field(VoteEntity::userId), persistedUser.id())
                .create();

        var persisted = voteRepository.save(entity);

        Assertions.assertThat(persisted.id()).isNotNull();
    }
}
