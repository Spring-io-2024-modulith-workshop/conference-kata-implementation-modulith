package com.acme.conferencesystem.users.business;

import com.acme.conferencesystem.users.persistence.UserEntity;
import com.acme.conferencesystem.users.persistence.UsersRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UsersRepository repository;

    @Spy
    UserMapper mapper = new UserMapperImpl();

    @Test
    void get_all_users() {
        List<UserEntity> entities = Instancio.ofList(UserEntity.class).size(2).create();
        given(repository.findAll()).willReturn(entities);

        List<User> users = service.getAllUsers();

        assertThat(users).hasSize(2);
    }

    @Test
    void register_user() {
        var user = Instancio.create(User.class);
        var userEntity = mapper.userToEntity(user);
        given(repository.save(userEntity)).willReturn(userEntity);

        var registeredUser = service.registerUser(user);

        then(repository).should().save(userEntity);
        assertThat(registeredUser).isEqualTo(user);
    }

    @Test
    void get_user_by_id() {
        var entity = Instancio.create(UserEntity.class);
        var user = mapper.entityToUser(entity);
        var id = entity.id();
        given(repository.findById(id)).willReturn(Optional.of(entity));

        Optional<User> result = service.getUserById(id);

        assertThat(result).isPresent().contains(user);
    }

    @Nested
    class Validate_user {

        private static final UUID userId = UUID.randomUUID();
        private static final boolean USER_IS_VALID = true;
        private static final boolean USER_IS_NOT_VALID = false;

        @Test
        void throw_exception_if_user_is_not_found() {
            given(repository.existsById(userId)).willReturn(USER_IS_NOT_VALID);
            assertThatIllegalArgumentException().isThrownBy(() -> service.validateUser(userId));
        }

        @Test
        void user_is_valid_if_user_is_found() {
            given(repository.existsById(userId)).willReturn(USER_IS_VALID);

            assertThatNoException().isThrownBy(() -> service.validateUser(userId));
        }

    }
}