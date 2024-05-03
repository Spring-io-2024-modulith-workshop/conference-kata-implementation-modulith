package com.acme.conferencesystem.voting.business;

import com.acme.conferencesystem.cfp_proposals.business.Proposal;
import com.acme.conferencesystem.cfp_proposals.business.ProposalService;
import com.acme.conferencesystem.cfp_proposals.business.ProposalStatus;
import com.acme.conferencesystem.users.business.User;
import com.acme.conferencesystem.users.business.UserRole;
import com.acme.conferencesystem.users.business.UserService;
import com.acme.conferencesystem.voting.persistence.VoteEntity;
import com.acme.conferencesystem.voting.persistence.VoteRepository;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;

    @Mock
    UserService usersService;

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
        User user = Instancio.of(User.class)
                .set(Select.field("role"), UserRole.ORGANIZER)
                .create();
        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposal"), proposal)
                .create();
        VoteEntity voteEntity = voteMapper.toEntity(vote);

        given(proposalService.getProposalById(any(UUID.class)))
                .willReturn(Optional.of(proposal));

        given(usersService.getUserById(any(UUID.class)))
                .willReturn(Optional.of(user));

        given(voteRepository.save(voteEntity)).willReturn(voteEntity);


        Vote votedProposal = voteService.voteProposal(vote);

        assertThat(votedProposal).isNotNull();
    }

    @Test
    void vote_invalid_user_proposal() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.NEW)
                .create();
        User user = Instancio.of(User.class)
                .set(Select.field("role"), UserRole.ATTENDEE)
                .create();
        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposal"), proposal)
                .create();

        given(proposalService.getProposalById(any(UUID.class)))
                .willReturn(Optional.of(proposal));

        given(usersService.getUserById(any(UUID.class)))
                .willReturn(Optional.of(user));


        assertThatIllegalArgumentException().isThrownBy(() -> voteService.voteProposal(vote));
    }

    @Test
    void vote_invalid_status_proposal() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.REJECTED)
                .create();
        User user = Instancio.of(User.class)
                .set(Select.field("role"), UserRole.ORGANIZER)
                .create();
        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposal"), proposal)
                .create();

        given(proposalService.getProposalById(any(UUID.class)))
                .willReturn(Optional.of(proposal));

        given(usersService.getUserById(any(UUID.class)))
                .willReturn(Optional.of(user));


        assertThatIllegalArgumentException().isThrownBy(() -> voteService.voteProposal(vote));
    }

    @Test
    void vote_valid_talk() {
        Proposal proposal = Instancio.of(Proposal.class)
                .set(Select.field("status"), ProposalStatus.ACCEPTED)
                .create();
        Vote vote = Instancio.of(Vote.class)
                .set(Select.field("proposal"), proposal)
                .create();
        VoteEntity voteEntity = voteMapper.toEntity(vote);

        given(proposalService.getProposalById(any(UUID.class)))
                .willReturn(Optional.of(proposal));
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
                .set(Select.field("proposal"), proposal)
                .create();

        given(proposalService.getProposalById(any(UUID.class)))
                .willReturn(Optional.of(proposal));

        assertThatIllegalArgumentException().isThrownBy(() -> voteService.voteTalk(vote));
    }
}