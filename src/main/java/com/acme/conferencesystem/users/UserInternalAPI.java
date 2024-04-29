package com.acme.conferencesystem.users;

import java.util.UUID;

public interface UserInternalAPI {
    void validateUser(UUID userId);
}
