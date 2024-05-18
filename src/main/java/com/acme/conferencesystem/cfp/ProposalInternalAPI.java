package com.acme.conferencesystem.cfp;

import java.util.List;
import java.util.UUID;

public interface ProposalInternalAPI {

    List<Proposal> getAcceptedProposals();

    Proposal getProposalById(UUID id);

    void validateProposalIsAccepted(UUID proposalId);

    void validateProposalIsNew(UUID proposalId);
}
