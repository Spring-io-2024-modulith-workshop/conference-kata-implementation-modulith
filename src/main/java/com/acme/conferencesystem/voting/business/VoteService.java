package com.acme.conferencesystem.voting.business;

import com.acme.conferencesystem.cfp_proposals.business.Proposal;
import com.acme.conferencesystem.cfp_proposals.business.ProposalService;
import com.acme.conferencesystem.cfp_proposals.business.ProposalStatus;
import com.acme.conferencesystem.users.business.User;
import com.acme.conferencesystem.users.business.UserRole;
import com.acme.conferencesystem.users.business.UserService;
import com.acme.conferencesystem.voting.persistence.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ProposalService proposalService;
    private final UserService userService;

    private final VoteMapper voteMapper;

    public VoteService(VoteRepository voteRepository, ProposalService proposalService, UserService userService, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.proposalService = proposalService;
        this.userService = userService;
        this.voteMapper = voteMapper;
    }

    public Vote voteProposal(Vote vote) {
        Optional<Proposal> proposalById = proposalService.getProposalById(vote.proposal().id());
        Optional<User> userById = userService.getUserById(vote.user().id());

        if (userById.isPresent() && !userById.get().role().equals(UserRole.ORGANIZER)) {
            throw new IllegalArgumentException("Only organizers can vote for proposals");
        }

        if (proposalById.isPresent() && !proposalById.get().status().equals(ProposalStatus.NEW)) {
            throw new IllegalArgumentException("Only new proposals can be voted for");
        }
        var voteEntity = voteRepository.save(voteMapper.toEntity(vote));
        return voteMapper.toVote(voteEntity);
    }

    public Vote voteTalk(Vote vote) {
        Optional<Proposal> proposalById = proposalService.getProposalById(vote.proposal().id());
        if (proposalById.isPresent() && !proposalById.get().status().equals(ProposalStatus.ACCEPTED)) {
            throw new IllegalArgumentException("Only accepted proposals can be voted for");
        }
        var voteEntity = voteRepository.save(voteMapper.toEntity(vote));
        return voteMapper.toVote(voteEntity);
    }
}
