package com.acme.conferencesystem.users.business;

import jakarta.validation.constraints.Email;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import java.util.UUID;

@Entity
public record User(@Identity UUID id, String name, @Email String email,
                   String phone, UserRole role) {
}
