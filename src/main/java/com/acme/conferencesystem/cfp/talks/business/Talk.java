package com.acme.conferencesystem.cfp.talks.business;

import java.util.UUID;

public record Talk(UUID id,
                   String title,
                   String description,
                   UUID speakerId) {

}
