package com.acme.conferencesystem.cfp_proposals.persistence;


import com.acme.conferencesystem.cfp_proposals.business.ProposalStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.acme.conferencesystem.cfp_proposals.business.ProposalStatus.ACCEPTED;
import static com.acme.conferencesystem.cfp_proposals.business.ProposalStatus.REJECTED;
import static java.time.LocalDateTime.now;

@Table("proposals")
public record ProposalEntity(
        @Id UUID id,
        String title,
        String description,
        @Column("speaker_id") UUID speakerId,
        ProposalStatus status,
        @CreatedDate LocalDateTime creationDateTime,
        @LastModifiedDate LocalDateTime lastModifiedDateTime
) {
    public ProposalEntity ofAccepted() {
        return new ProposalEntity(id, title, description, speakerId, ACCEPTED, creationDateTime, now());
    }

    public ProposalEntity ofRejected() {
        return new ProposalEntity(id, title, description, speakerId, REJECTED, creationDateTime, now());
    }
}
