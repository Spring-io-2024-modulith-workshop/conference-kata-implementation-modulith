package com.acme.conferencesystem.voting.business;

import com.acme.conferencesystem.cfp.ProposalInternalAPI;
import com.acme.conferencesystem.users.UserInternalAPI;
import com.acme.conferencesystem.voting.persistence.VoteRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ProposalInternalAPI proposalService;
    private final UserInternalAPI userService;
    private final VoteMapper voteMapper;

    VoteService(VoteRepository voteRepository, VoteMapper voteMapper, ProposalInternalAPI proposalService, UserInternalAPI userService) {
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
