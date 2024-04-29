package com.acme.conferencesystem.users.business;

import com.acme.conferencesystem.users.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserEntity userToEntity(User user);

    User entityToUser(UserEntity entity);
}
