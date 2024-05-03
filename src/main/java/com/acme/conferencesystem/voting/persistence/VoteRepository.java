package com.acme.conferencesystem.voting.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, UUID> {
}
