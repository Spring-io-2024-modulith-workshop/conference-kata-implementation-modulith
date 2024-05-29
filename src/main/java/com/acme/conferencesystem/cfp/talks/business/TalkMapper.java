package com.acme.conferencesystem.cfp.talks.business;

import com.acme.conferencesystem.cfp.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TalkMapper {

    Talk proposalToTalk(Proposal proposal);
}
