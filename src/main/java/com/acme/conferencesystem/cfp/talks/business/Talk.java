package com.acme.conferencesystem.cfp.talks.business;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import java.util.UUID;

@Entity
public record Talk(@Identity UUID id,
                   String title,
                   String description,
                   UUID speakerId) {

}
