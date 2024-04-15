package com.acme.conferencesystem.users.business;

import com.acme.conferencesystem.users.persistence.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity userToEntity(User user);

    User entityToUser(UserEntity entity);
}
