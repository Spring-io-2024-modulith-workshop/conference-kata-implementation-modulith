package com.acme.conferencesystem.cfp.proposals.business;

import static com.acme.conferencesystem.cfp.proposals.business.ProposalStatus.ACCEPTED;
import static com.acme.conferencesystem.cfp.proposals.business.ProposalStatus.NEW;
import static com.acme.conferencesystem.cfp.proposals.business.ProposalStatus.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.instancio.Instancio.create;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.cfp.ProposalAcceptedEvent;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.UserInternalAPI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

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
        var proposal = create(Proposal.class);
        var proposalEntity = mapper.proposalToEntity(proposal);
        given(repository.save(proposalEntity)).willReturn(proposalEntity);

        var submittedProposal = service.submitProposal(proposal);

        then(repository).should().save(proposalEntity);
        assertThat(submittedProposal).isEqualTo(proposal);
    }

    @Test
    void reject_proposals_when_invalid_speaker() {
        var proposal = create(Proposal.class);

        willThrow(new RuntimeException())
                .given(userInternalAPI).validateUser(proposal.speakerId());

        assertThatRuntimeException()
                .isThrownBy(() -> service.submitProposal(proposal));

        then(repository)
                .should(never()).save(any());
    }

    @Test
    void get_proposal_by_id() {
        var entity = create(ProposalEntity.class);
        var id = entity.id();
        given(repository.findById(id)).willReturn(Optional.of(entity));

        assertThat(service.getProposalById(id)).isNotNull();
    }

    @Test
    void validate_proposal_is_new() {
        UUID id = UUID.randomUUID();
        given(repository.findById(id))
                .willReturn(Optional.of(Instancio.of(ProposalEntity.class)
                        .set(field(ProposalEntity::status), NEW)
                        .create()));

        assertThatNoException()
                .isThrownBy(() -> service.validateProposalIsNew(id));
    }

    @Test
    void validate_proposal_is_new_throws_exception() {
        UUID id = UUID.randomUUID();
        given(repository.findById(id))
                .willReturn(Optional.of(Instancio.of(ProposalEntity.class)
                        .set(field(ProposalEntity::status), ACCEPTED)
                        .create()));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.validateProposalIsNew(id));
    }

    @Test
    void validate_proposal_is_accepted() {
        UUID id = UUID.randomUUID();
        given(repository.findById(id))
                .willReturn(Optional.of(Instancio.of(ProposalEntity.class)
                        .set(field(ProposalEntity::status), ACCEPTED)
                        .create()));

        assertThatNoException()
                .isThrownBy(() -> service.validateProposalIsAccepted(id));
    }

    @Test
    void validate_proposal_is_accepted_throws_exception() {
        UUID id = UUID.randomUUID();
        given(repository.findById(id))
                .willReturn(Optional.of(Instancio.of(ProposalEntity.class)
                        .set(field(ProposalEntity::status), REJECTED)
                        .create()));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.validateProposalIsAccepted(id));
    }

    @Test
    void reject_proposal() {
        var proposal = create(Proposal.class);
        var entity = mapper.proposalToEntity(proposal);
        given(repository.findById(entity.id())).willReturn(Optional.of(entity));
        given(repository.save(Mockito.any())).willReturn(entity);

        service.rejectProposal(proposal.id());

        verify(repository, atMostOnce()).save(any(ProposalEntity.class));
    }

    @Test
    void approve_proposal() {
        var proposal = create(Proposal.class);
        var entity = mapper.proposalToEntity(proposal);
        given(repository.findById(entity.id())).willReturn(Optional.of(entity));
        given(repository.save(Mockito.any())).willReturn(entity);

        service.approveProposal(proposal.id());

        verify(repository, atMostOnce()).save(any(ProposalEntity.class));
        verify(eventPublisher, atMostOnce()).publishEvent(ProposalAcceptedEvent.class);
    }

}
