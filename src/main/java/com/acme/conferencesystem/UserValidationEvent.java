package com.acme.conferencesystem;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class UserValidationEvent extends ApplicationEvent {

    private final UUID userId;

    public UserValidationEvent(Object source, UUID userId) {
        super(source);
        this.userId = userId;
    }


    public UUID getUserId() {
        return userId;
    }

}
