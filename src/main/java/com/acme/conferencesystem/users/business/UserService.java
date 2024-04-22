package com.acme.conferencesystem.users.business;

import com.acme.conferencesystem.UserValidationEvent;
import com.acme.conferencesystem.users.persistence.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Service
public class UserService {

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

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    void on(UserValidationEvent event) {
        UUID userId = event.getUserId();
        log.info("Received UserValidationEvent for user with ID: {} ", userId);

        if (!isUserValid(userId)) {
            log.info("User with ID {}, not valid.", userId);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new IllegalArgumentException("User not valid.");
        }
    }

    private boolean isUserValid(UUID userId) {
        return false;
    }

}
