package com.acme.conferencesystem.cfp_proposals;

import com.acme.conferencesystem.cfp_proposals.business.Proposal;

import java.util.UUID;

public interface ProposalInternalAPI {

    Proposal getProposalById(UUID id);

    void validateProposalIsAccepted(UUID proposalId);

    void validateProposalIsNew(UUID proposalId);
}
