package com.acme.conferencesystem.voting.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import com.acme.conferencesystem.cfp.proposals.business.Proposal;
import com.acme.conferencesystem.cfp.proposals.business.ProposalService;
import com.acme.conferencesystem.cfp.proposals.business.ProposalStatus;
import com.acme.conferencesystem.users.business.UserService;
import com.acme.conferencesystem.voting.persistence.VoteEntity;
import com.acme.conferencesystem.voting.persistence.VoteRepository;
import java.util.UUID;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;

    @Mock
    UserService userService;

    @Mock
    ProposalService proposalService;

    @InjectMocks
    VoteService voteService;

    @Spy
    VoteMapper voteMapper = new VoteMapperImpl();

    @Test
    void vote_valid_proposal() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.NEW)
                .create();

        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposalId"), proposal.id())
                .create();

        VoteEntity entity = voteMapper.toEntity(vote);

        given(voteRepository.save(entity)).willReturn(entity);

        Vote votedProposal = voteService.voteProposal(vote);

        assertThat(votedProposal).isNotNull();
    }

    @Test
    void vote_invalid_user_proposal() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.NEW)
                .create();

        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposalId"), proposal.id())
                .create();
        doThrow(new IllegalArgumentException("User is not allowed to vote"))
                .when(userService).validateUserIsOrganizer(any(UUID.class));

        assertThatIllegalArgumentException().isThrownBy(() -> voteService.voteProposal(vote));
    }

    @Test
    void vote_invalid_status_proposal() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.REJECTED)
                .create();

        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposalId"), proposal.id())
                .create();

        doThrow(new IllegalArgumentException("Proposal is not allowed to be voted"))
                .when(proposalService)
                .validateProposalIsNew(vote.proposalId());

        assertThatIllegalArgumentException().isThrownBy(() -> voteService.voteProposal(vote));
    }

    @Test
    void vote_valid_talk() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.ACCEPTED)
                .create();
        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposalId"), proposal.id())
                .create();
        VoteEntity voteEntity = voteMapper.toEntity(vote);

        given(voteRepository.save(voteEntity)).willReturn(voteEntity);

        Vote votedProposal = voteService.voteTalk(vote);

        assertThat(votedProposal).isNotNull();
    }

    @Test
    void vote_invalid_status_talk() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.REJECTED)
                .create();
        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposalId"), proposal.id())
                .create();

        doThrow(new IllegalArgumentException("Proposal is not allowed to be voted"))
                .when(proposalService).validateProposalIsAccepted(vote.proposalId());

        assertThatIllegalArgumentException().isThrownBy(() -> voteService.voteTalk(vote));
    }

    @Test
    void should_map_correctly_between_vote_and_vote_entity() {
        Vote vote = Instancio.of(Vote.class).create();
        VoteEntity voteEntity = voteMapper.toEntity(vote);

        assertThat(voteEntity.proposalId()).isEqualTo(vote.proposalId());
    }
}
