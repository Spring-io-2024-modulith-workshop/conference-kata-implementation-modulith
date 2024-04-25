package com.acme.conferencesystem.users.business;

import com.acme.conferencesystem.UserValidationEvent;
import com.acme.conferencesystem.users.persistence.UserEntity;
import com.acme.conferencesystem.users.persistence.UsersRepository;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;
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

        @Test
        void throw_exception_if_user_is_not_found() {
            var userValidationEvent = Instancio.create(UserValidationEvent.class);
            given(repository.existsById(userValidationEvent.getUserId())).willReturn(false);

            Assertions.assertThatThrownBy(() -> service.onValidateUserEvent(userValidationEvent));
        }

        @Test
        void user_is_valid_if_user_is_found() {
            var userValidationEvent = Instancio.create(UserValidationEvent.class);
            given(repository.existsById(userValidationEvent.getUserId())).willReturn(true);

            Assertions.assertThatNoException().isThrownBy(() -> service.onValidateUserEvent(userValidationEvent));
        }
        
    }
}