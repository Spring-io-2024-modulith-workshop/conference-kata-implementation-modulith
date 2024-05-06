package com.acme.conferencesystem.cfp_proposals.business;

import com.acme.conferencesystem.cfp_proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp_proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.UserInternalAPI;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProposalServiceTest {

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

        assertThatRuntimeException()
                .isThrownBy(() -> service.submitProposal(proposal));

        then(repository)
                .should(never()).save(any());
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

    @Test
    void reject_proposal() {
        var proposal = Instancio.create(Proposal.class);
        var entity = mapper.proposalToEntity(proposal);
        given(repository.findById(entity.id())).willReturn(Optional.of(entity));
        given(repository.save(Mockito.any())).willReturn(entity);

        service.rejectProposal(proposal.id());
        verify(repository, atMostOnce()).save(any(ProposalEntity.class));

    }

    @Test
    void approve_proposal() {
        var proposal = Instancio.create(Proposal.class);
        var entity = mapper.proposalToEntity(proposal);
        given(repository.findById(entity.id())).willReturn(Optional.of(entity));
        given(repository.save(Mockito.any())).willReturn(entity);

        service.approveProposal(proposal.id());
        verify(repository, atMostOnce()).save(any(ProposalEntity.class));
    }
}