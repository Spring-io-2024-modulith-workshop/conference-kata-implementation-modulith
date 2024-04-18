package com.acme.conferencesystem.users.business;

import jakarta.validation.constraints.Email;

import java.util.UUID;

public record User(UUID id, String name, @Email String email, String phone, UserRole role) {
}
