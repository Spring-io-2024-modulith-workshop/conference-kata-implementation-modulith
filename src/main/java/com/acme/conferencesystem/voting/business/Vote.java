package com.acme.conferencesystem.voting.business;

import com.acme.conferencesystem.cfp_proposals.business.Proposal;
import com.acme.conferencesystem.users.business.User;

import java.util.UUID;

public record Vote(UUID id,
                   User user,
                   Proposal proposal,
                   Integer rating
) {
}
