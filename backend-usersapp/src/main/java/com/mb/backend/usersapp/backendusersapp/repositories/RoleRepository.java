package com.mb.backend.usersapp.backendusersapp.repositories;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mb.backend.usersapp.backendusersapp.models.entities.Role;
// import java.util.List;


public interface RoleRepository 
        extends CrudRepository<Role, Long> {
                Optional<Role> findByName(String name);


}
