package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    public UserResponse register(RegisterRequest request) {

        log.info("==============================================");
        log.info("USER SERVICE - REGISTER API");
        log.info("==============================================");

        log.info("Received RegisterRequest:");
        log.info("KeycloakId : {}", request.getKeycloakId());
        log.info("Email      : {}", request.getEmail());
        log.info("First Name : {}", request.getFirstName());
        log.info("Last Name  : {}", request.getLastName());

        if (repository.existsByEmail(request.getEmail())) {

            log.info("User already exists with email: {}", request.getEmail());

            User existingUser = repository.findByEmail(request.getEmail());

            log.info("Existing User:");
            log.info("Database Id : {}", existingUser.getId());
            log.info("KeycloakId  : {}", existingUser.getKeycloakId());

            UserResponse response = new UserResponse();
            response.setId(existingUser.getId());
            response.setKeycloakId(existingUser.getKeycloakId());
            response.setEmail(existingUser.getEmail());
            response.setPassword(existingUser.getPassword());
            response.setFirstName(existingUser.getFirstName());
            response.setLastName(existingUser.getLastName());
            response.setCreatedAt(existingUser.getCreatedAt());
            response.setUpdatedAt(existingUser.getUpdatedAt());

            return response;
        }

        log.info("User does not exist. Creating new user...");

        User user = new User();

        user.setKeycloakId(request.getKeycloakId());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        log.info("Saving User:");
        log.info("KeycloakId : {}", user.getKeycloakId());
        log.info("Email      : {}", user.getEmail());

        User savedUser = repository.save(user);

        log.info("User Saved Successfully!");
        log.info("Database Id : {}", savedUser.getId());
        log.info("KeycloakId  : {}", savedUser.getKeycloakId());

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setKeycloakId(savedUser.getKeycloakId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        log.info("Returning Response:");
        log.info("Database Id : {}", response.getId());
        log.info("KeycloakId  : {}", response.getKeycloakId());

        log.info("==============================================");

        return response;
    }

    public UserResponse getUserProfile(String userId) {

        log.info("==============================================");
        log.info("GET USER PROFILE");
        log.info("UserId: {}", userId);

        User user = repository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User Not Found: {}", userId);
                    return new RuntimeException("User Not Found");
                });

        log.info("User Found:");
        log.info("Database Id : {}", user.getId());
        log.info("KeycloakId  : {}", user.getKeycloakId());

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setKeycloakId(user.getKeycloakId());
        response.setPassword(user.getPassword());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }

    public Boolean existByUserId(String userId) {

        log.info("==============================================");
        log.info("VALIDATE USER");
        log.info("Incoming KeycloakId : {}", userId);

        Boolean exists = repository.existsByKeycloakId(userId);

        log.info("Exists in Database : {}", exists);

        if (exists) {
            User user = repository.findByEmail(
                    repository.findAll().stream()
                            .filter(u -> userId.equals(u.getKeycloakId()))
                            .findFirst()
                            .orElseThrow()
                            .getEmail()
            );

            log.info("Found User:");
            log.info("Database Id : {}", user.getId());
            log.info("KeycloakId  : {}", user.getKeycloakId());
            log.info("Email       : {}", user.getEmail());
        }

        log.info("==============================================");

        return exists;
    }
}