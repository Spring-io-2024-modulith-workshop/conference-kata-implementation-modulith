package com.acme.conferencesystem.cfp.talks.business;

import com.acme.conferencesystem.cfp.proposals.business.ProposalService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TalkService {

    private final ProposalService proposalService;
    private final TalkMapper mapper;

    TalkService(ProposalService proposalService, TalkMapper mapper) {
        this.proposalService = proposalService;
        this.mapper = mapper;
    }

    public List<Talk> getTalks() {
        return proposalService.getAcceptedProposals()
                .stream()
                .map(mapper::proposalToTalk)
                .toList();
    }

}
