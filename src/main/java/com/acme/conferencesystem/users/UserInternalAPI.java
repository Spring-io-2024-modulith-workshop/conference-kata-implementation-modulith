package com.acme.conferencesystem.users;

import com.acme.conferencesystem.users.business.User;

import java.util.Optional;
import java.util.UUID;

public interface UserInternalAPI {
    void validateUserIsOrganizer(UUID userId);

    void validateUser(UUID userId);

    Optional<User> getUserById(UUID id);
}
