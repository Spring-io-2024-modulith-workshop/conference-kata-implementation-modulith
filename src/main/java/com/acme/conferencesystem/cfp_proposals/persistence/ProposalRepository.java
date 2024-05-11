package com.acme.conferencesystem.cfp_proposals.persistence;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends CrudRepository<ProposalEntity, UUID> {

}
