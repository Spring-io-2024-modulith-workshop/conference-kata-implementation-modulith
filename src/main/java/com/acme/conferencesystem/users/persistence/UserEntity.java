package com.acme.conferencesystem.users.persistence;

import com.acme.conferencesystem.users.business.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("users")
public record UserEntity(@Id UUID id, String name, String email, String phone, UserRole role) {
}
