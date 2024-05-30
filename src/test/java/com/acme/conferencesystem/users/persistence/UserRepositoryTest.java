package com.acme.conferencesystem.users.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

import com.acme.conferencesystem.ContainerConfig;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.transaction.annotation.Transactional;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(ContainerConfig.class)
class UserRepositoryTest {

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
