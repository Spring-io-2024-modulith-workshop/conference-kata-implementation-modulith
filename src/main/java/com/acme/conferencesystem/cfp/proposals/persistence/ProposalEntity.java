package com.acme.conferencesystem.cfp.proposals.persistence;


import com.acme.conferencesystem.cfp.proposals.business.ProposalStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static com.acme.conferencesystem.cfp.proposals.business.ProposalStatus.ACCEPTED;
import static com.acme.conferencesystem.cfp.proposals.business.ProposalStatus.REJECTED;
import static java.time.LocalDateTime.now;

@Table("proposals")
public record ProposalEntity(
        @Id UUID id,
        String title,
        String description,
        @Column("speaker_id") UUID speakerId,
        ProposalStatus status,
        @ReadOnlyProperty @CreatedDate LocalDateTime creationDateTime,
        @LastModifiedDate LocalDateTime lastModifiedDateTime
) {

    public ProposalEntity createWithAcceptedStatus() {
        return new ProposalEntity(id, title, description, speakerId, ACCEPTED, creationDateTime, now(ZoneId.systemDefault()));
    }

    public ProposalEntity createWithRejectedStatus() {
        return new ProposalEntity(id, title, description, speakerId, REJECTED, creationDateTime, now(ZoneId.systemDefault()));
    }
}
