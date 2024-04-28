package com.acme.conferencesystem.cfp_proposals.business;

import com.acme.conferencesystem.cfp_proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp_proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.UserInternalAPI;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProposalServiceTest {

    private static final boolean NOT_VALID_USER = false;

    @InjectMocks
    ProposalService service;

    @Mock
    ProposalRepository repository;

    @Spy
    ProposalMapper mapper = new ProposalMapperImpl();

    @Mock
    UserInternalAPI userInternalAPI;


    @Test
    void get_all_proposals() {
        List<ProposalEntity> entities = Instancio.ofList(ProposalEntity.class).size(2).create();
        given(repository.findAll()).willReturn(entities);

        List<Proposal> proposals = service.getAllProposals();

        assertThat(proposals).hasSize(2);
    }

    @Test
    void submit_proposal() {
        var proposal = Instancio.create(Proposal.class);
        var proposalEntity = mapper.proposalToEntity(proposal);
        given(repository.save(proposalEntity)).willReturn(proposalEntity);

        var submittedProposal = service.submitProposal(proposal);

        then(repository).should().save(proposalEntity);
        assertThat(submittedProposal).isEqualTo(proposal);
    }

    @Test
    void reject_proposals_when_invalid_speaker() {
        var proposal = Instancio.create(Proposal.class);
        willThrow(new RuntimeException())
                .given(userInternalAPI).validateUser(proposal.speakerId());

        ThrowingCallable submitProposalThrowsNotValidUser
                = () -> service.submitProposal(proposal);
        assertThatThrownBy(submitProposalThrowsNotValidUser);
        
        then(repository).should(never()).save(any());
    }

    @Test
    void get_proposal_by_id() {
        var entity = Instancio.create(ProposalEntity.class);
        var proposal = mapper.entityToProposal(entity);
        var id = entity.id();
        given(repository.findById(id)).willReturn(Optional.of(entity));

        Optional<Proposal> result = service.getProposalById(id);

        assertThat(result).isPresent().contains(proposal);
    }
}