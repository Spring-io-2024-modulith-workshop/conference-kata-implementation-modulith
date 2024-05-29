package com.acme.conferencesystem.cfp.proposals.business;

import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ProposalMapper {

    ProposalEntity proposalToEntity(Proposal proposal);

    Proposal entityToProposal(ProposalEntity entity);
}
