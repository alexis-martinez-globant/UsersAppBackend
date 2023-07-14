package com.mb.backend.usersapp.backendusersapp.services;

import java.util.List;
import java.util.Optional;

import com.mb.backend.usersapp.backendusersapp.models.entities.User;
import com.mb.backend.usersapp.backendusersapp.models.entities.UserRequest;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);
    Optional<User> update(UserRequest user, Long id);

    void remove(Long id);
}
