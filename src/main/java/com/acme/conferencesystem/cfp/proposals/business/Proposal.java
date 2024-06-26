package com.acme.conferencesystem.cfp.proposals.business;

import java.util.UUID;

public record Proposal(UUID id,
                       String title,
                       String description,
                       UUID speakerId,
                       ProposalStatus status) {

}
