package com.acme.conferencesystem.cfp_proposals.business;

import com.acme.conferencesystem.cfp_proposals.persistence.ProposalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProposalMapper {
    ProposalEntity proposalToEntity(Proposal proposal);

    Proposal entityToProposal(ProposalEntity entity);
}
