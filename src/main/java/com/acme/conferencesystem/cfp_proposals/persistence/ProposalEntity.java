package com.acme.conferencesystem.cfp_proposals.persistence;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("proposals")
public record ProposalEntity(
        @Id UUID id,
        String title,
        String description,
        @Column("speaker_id") UUID speakerId,
        @CreatedDate LocalDateTime creationDateTime
) {
}
