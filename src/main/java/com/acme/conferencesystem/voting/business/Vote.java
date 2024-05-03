package com.acme.conferencesystem.voting.business;

import java.util.UUID;

public record Vote(
        UUID id,
        UUID userId,
        UUID proposalId,
        Integer rating
) {
}
