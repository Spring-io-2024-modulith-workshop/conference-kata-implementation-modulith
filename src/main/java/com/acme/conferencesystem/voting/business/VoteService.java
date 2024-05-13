package com.acme.conferencesystem.voting.business;

import com.acme.conferencesystem.cfp.ProposalInternalAPI;
import com.acme.conferencesystem.users.UserInternalAPI;
import com.acme.conferencesystem.voting.persistence.VoteRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ProposalInternalAPI proposalInternalAPI;
    private final UserInternalAPI userInternalAPI;
    private final VoteMapper voteMapper;

    VoteService(VoteRepository voteRepository, VoteMapper voteMapper, ProposalInternalAPI proposalInternalAPI, UserInternalAPI userInternalAPI) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.proposalInternalAPI = proposalInternalAPI;
        this.userInternalAPI = userInternalAPI;
    }

    public Vote voteProposal(@NotNull Vote vote) {
        userInternalAPI.validateUserIsOrganizer(vote.userId());
        proposalInternalAPI.validateProposalIsNew(vote.proposalId());

        var voteEntity = voteRepository.save(voteMapper.toEntity(vote));
        return voteMapper.toVote(voteEntity);
    }

    public Vote voteTalk(@NotNull Vote vote) {
        proposalInternalAPI.validateProposalIsAccepted(vote.proposalId());

        var voteEntity = voteRepository.save(voteMapper.toEntity(vote));
        return voteMapper.toVote(voteEntity);
    }
}
