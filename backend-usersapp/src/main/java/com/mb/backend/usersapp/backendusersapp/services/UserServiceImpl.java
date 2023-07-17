package com.mb.backend.usersapp.backendusersapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mb.backend.usersapp.backendusersapp.models.entities.User;
import com.mb.backend.usersapp.backendusersapp.models.entities.UserRequest;
import com.mb.backend.usersapp.backendusersapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    
    @Override
    @Transactional(readOnly=true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }
    
    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {
        Optional<User> optionalUser = this.findById(id);
        User userUpdated = null;
        if (optionalUser.isPresent()) {
            User userDB = optionalUser.orElseThrow();
            userDB.setUsername(user.getUsername());
            userDB.setEmail(user.getEmail());
            userUpdated = this.save(userDB);
        }
        return Optional.ofNullable(userUpdated);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }
    
}
