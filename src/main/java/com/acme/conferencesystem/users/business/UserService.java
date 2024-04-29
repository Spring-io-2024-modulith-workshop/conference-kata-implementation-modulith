package com.acme.conferencesystem.users.business;

import com.acme.conferencesystem.users.UserInternalAPI;
import com.acme.conferencesystem.users.persistence.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserInternalAPI {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UsersRepository repository;
    private final UserMapper mapper;

    public UserService(UsersRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<User> getAllUsers() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::entityToUser)
                .collect(Collectors.toList());
    }

    public User registerUser(User puser) {
        var puserEntity = mapper.userToEntity(puser);
        var registeredUserEntity = repository.save(puserEntity);
        return mapper.entityToUser(registeredUserEntity);
    }

    public Optional<User> getUserById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToUser);
    }
    
    public void validateUser(UUID userId) {
        if (!isUserValid(userId)) {
            log.error("User with ID {}, is not valid.", userId);
            throw new IllegalArgumentException("User with ID %s, is not valid".formatted(userId));
        }
    }

    private boolean isUserValid(UUID userId) {
        return repository.existsById(userId);
    }

}
