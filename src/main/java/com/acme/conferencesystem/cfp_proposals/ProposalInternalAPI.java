package com.acme.conferencesystem.cfp_proposals;

import com.acme.conferencesystem.cfp_proposals.business.Proposal;

import java.util.Optional;
import java.util.UUID;

public interface ProposalInternalAPI {

    Optional<Proposal> getProposalById(UUID id);

    void validateProposalIsAccepted(UUID proposalId);

    void validateProposalIsNew(UUID proposalId);
}
