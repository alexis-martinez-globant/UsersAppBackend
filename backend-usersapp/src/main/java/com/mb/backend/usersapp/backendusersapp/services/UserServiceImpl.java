package com.mb.backend.usersapp.backendusersapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mb.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.mb.backend.usersapp.backendusersapp.models.dto.mapper.DtoMapperUser;
import com.mb.backend.usersapp.backendusersapp.models.entities.IUser;
import com.mb.backend.usersapp.backendusersapp.models.entities.Role;
import com.mb.backend.usersapp.backendusersapp.models.entities.User;
import com.mb.backend.usersapp.backendusersapp.models.entities.UserRequest;
import com.mb.backend.usersapp.backendusersapp.repositories.RoleRepository;
import com.mb.backend.usersapp.backendusersapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional(readOnly=true)
    public List<UserDto> findAll() {
        List<User> users = (List<User>) repository.findAll();
        return users
            .stream()
            .map(u -> DtoMapperUser.builder().setUser(u).build())
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<UserDto> findById(Long id) {
        return repository.findById(id).map(u -> DtoMapperUser.builder().setUser(u).build());
    }

    @Override
    @Transactional
    public UserDto save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(getRoles(user));

        return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }

    @Override
    @Transactional
    public Optional<UserDto> update(UserRequest user, Long id) {
        Optional<User> o = repository.findById(id);
        User userUpdated = null;
        if (o.isPresent()) {
            User userDB = o.orElseThrow();
            userDB.setRoles(getRoles(user));
            userDB.setUsername(user.getUsername());
            userDB.setEmail(user.getEmail());
            userUpdated = repository.save(userDB);
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(userUpdated).build());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    private List<Role> getRoles(IUser user){
        Optional<Role> ou = roleRepository.findByName("ROLE_USER");

            List<Role> roles = new ArrayList<>();
            if (ou.isPresent()) {
                roles.add(ou.orElseThrow());
            }
            if (user.isAdmin()) {
                Optional<Role> oa = roleRepository.findByName("ROLE_ADMIN");
                if (oa.isPresent()) {
                    roles.add(oa.orElseThrow());
                }
            }
            return roles;
    }
}
