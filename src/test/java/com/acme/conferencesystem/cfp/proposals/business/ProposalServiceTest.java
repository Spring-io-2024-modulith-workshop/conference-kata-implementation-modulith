package com.acme.conferencesystem.cfp.proposals.business;

import com.acme.conferencesystem.cfp.proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProposalServiceTest {

    @InjectMocks
    ProposalService service;

    @Mock
    ProposalRepository repository;

    @Spy
    ProposalMapper mapper = new ProposalMapperImpl();

    @Mock
    ApplicationEventPublisher eventPublisher;


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
    void get_proposal_by_id() {
        var entity = Instancio.create(ProposalEntity.class);
        var proposal = mapper.entityToProposal(entity);
        var id = entity.id();
        given(repository.findById(id)).willReturn(Optional.of(entity));

        Optional<Proposal> result = service.getProposalById(id);

        assertThat(result).isPresent().contains(proposal);
    }
}