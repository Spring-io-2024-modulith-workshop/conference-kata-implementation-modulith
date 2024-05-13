package com.acme.conferencesystem.voting.persistence;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("votes")
public record VoteEntity(
        @Id UUID id,
        @Column("proposal_id") UUID proposalId,
        @Column("user_id") UUID userId,
        Integer rating,
        @ReadOnlyProperty @CreatedDate LocalDateTime creationDateTime) {

}
