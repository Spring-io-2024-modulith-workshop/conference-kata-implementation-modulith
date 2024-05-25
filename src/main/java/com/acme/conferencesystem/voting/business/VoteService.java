package com.acme.conferencesystem.voting.business;

import com.acme.conferencesystem.cfp.proposals.business.ProposalService;
import com.acme.conferencesystem.users.business.UserService;
import com.acme.conferencesystem.voting.persistence.VoteRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ProposalService proposalService;
    private final UserService userService;
    private final VoteMapper voteMapper;

    VoteService(VoteRepository voteRepository, VoteMapper voteMapper, ProposalService proposalService, UserService userService) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.proposalService = proposalService;
        this.userService = userService;
    }

    public Vote voteProposal(@NotNull Vote vote) {
        userService.validateUserIsOrganizer(vote.userId());
        proposalService.validateProposalIsNew(vote.proposalId());

        var voteEntity = voteRepository.save(voteMapper.toEntity(vote));
        return voteMapper.toVote(voteEntity);
    }

    public Vote voteTalk(@NotNull Vote vote) {
        proposalService.validateProposalIsAccepted(vote.proposalId());

        var voteEntity = voteRepository.save(voteMapper.toEntity(vote));
        return voteMapper.toVote(voteEntity);
    }
}
