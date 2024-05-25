package com.acme.conferencesystem.cfp.proposals.events;

import com.acme.conferencesystem.cfp.proposals.business.Proposal;

public record ProposalAcceptedEvent(Proposal proposal) {

}
