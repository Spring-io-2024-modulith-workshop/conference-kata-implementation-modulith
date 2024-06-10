package com.acme.conferencesystem.cfp;

import com.acme.conferencesystem.cfp.proposals.business.ProposalStatus;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import java.util.UUID;

@Entity
public record Proposal(@Identity UUID id,
                       String title,
                       String description,
                       UUID speakerId,
                       ProposalStatus status) {

}
