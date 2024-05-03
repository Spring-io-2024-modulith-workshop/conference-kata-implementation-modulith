package com.acme.conferencesystem.voting.business;

import com.acme.conferencesystem.voting.persistence.VoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoteMapper {

    VoteEntity toEntity(Vote vote);

    Vote toVote(VoteEntity voteEntity);
}
