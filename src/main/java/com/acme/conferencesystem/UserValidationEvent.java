package com.acme.conferencesystem;

import java.util.UUID;

public class UserValidationEvent {

    private final UUID userId;

    public UserValidationEvent(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

}
