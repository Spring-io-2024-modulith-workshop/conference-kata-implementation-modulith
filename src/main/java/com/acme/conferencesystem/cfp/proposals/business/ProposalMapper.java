package com.acme.conferencesystem.cfp.proposals.business;

import com.acme.conferencesystem.cfp.proposals.persistence.ProposalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProposalMapper {
    ProposalEntity proposalToEntity(Proposal proposal);

    Proposal entityToProposal(ProposalEntity entity);
}
