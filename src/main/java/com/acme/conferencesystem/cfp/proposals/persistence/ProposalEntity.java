package com.acme.conferencesystem.cfp.proposals.persistence;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("PROPOSAL")
public record ProposalEntity(@Id UUID id, String title, String description, String speaker) {
}
