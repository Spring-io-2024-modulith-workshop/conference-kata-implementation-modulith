package com.acme.conferencesystem.voting.persistence;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("votes")
public record VoteEntity(
        @Id UUID id,
        @Column("proposalId") UUID proposalId,
        @Column("userId") UUID userId,
        Integer rating,
        @CreatedDate LocalDateTime creationDateTime) {
}
