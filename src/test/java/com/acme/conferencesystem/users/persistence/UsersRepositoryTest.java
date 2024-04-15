package com.acme.conferencesystem.users.persistence;

import com.acme.conferencesystem.AbstractIntegrationTest;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class UsersRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    UsersRepository repository;

    @Test
    @Transactional
    void save() {
        var entity = Instancio.of(UserEntity.class).ignore(field(UserEntity::id)).create();

        UserEntity persisted = repository.save(entity);

        assertThat(persisted.id()).isNotNull();
        assertThat(persisted)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(entity);
    }
}
