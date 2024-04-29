package com.acme.conferencesystem.cfp_proposals.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProposalRepository extends CrudRepository<ProposalEntity, UUID> {
}
