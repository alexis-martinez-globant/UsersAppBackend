package com.mb.backend.usersapp.backendusersapp.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mb.backend.usersapp.backendusersapp.models.entities.User;
// import java.util.List;


public interface UserRepository 
        extends CrudRepository<User, Long> {

                Optional<User> findByUsername(String username);

                @Query("select u from User u where u.username =?1")
                Optional<User> getUserByUsername(String username);

}
