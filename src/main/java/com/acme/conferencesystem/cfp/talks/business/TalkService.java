package com.acme.conferencesystem.cfp.talks.business;

import com.acme.conferencesystem.cfp.ProposalInternalAPI;
import com.acme.conferencesystem.cfp.TalkInternalAPI;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TalkService implements TalkInternalAPI {

    private final ProposalInternalAPI proposalInternalAPI;
    private final TalkMapper mapper;

    TalkService(ProposalInternalAPI proposalInternalAPI, TalkMapper mapper) {
        this.proposalInternalAPI = proposalInternalAPI;
        this.mapper = mapper;
    }

    public List<Talk> getTalks() {
        return proposalInternalAPI.getAcceptedProposals()
                .stream()
                .map(mapper::proposalToTalk)
                .toList();
    }

}
