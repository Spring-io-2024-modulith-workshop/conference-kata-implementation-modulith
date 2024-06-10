package com.acme.conferencesystem.voting.business;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import java.util.UUID;

@Entity
public record Vote(
        @Identity UUID id,
        UUID userId,
        UUID proposalId,
        Integer rating
) {
}
