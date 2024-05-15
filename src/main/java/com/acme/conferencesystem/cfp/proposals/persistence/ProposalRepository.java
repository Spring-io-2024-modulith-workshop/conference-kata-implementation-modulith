package com.acme.conferencesystem.cfp.proposals.persistence;

import com.acme.conferencesystem.cfp.proposals.business.ProposalStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends CrudRepository<ProposalEntity, UUID> {

    List<ProposalEntity> getProposalEntityByStatus(ProposalStatus proposalStatus);

}
