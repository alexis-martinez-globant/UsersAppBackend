package com.mb.backend.usersapp.backendusersapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mb.backend.usersapp.backendusersapp.models.entities.User;
import com.mb.backend.usersapp.backendusersapp.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list(){
        return service.findAll();
    };
    
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
       Optional<User> userOptional = service.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build(); // 404 
    }
    // @GetMapping("/{id}")
    // public User show(@PathVariable Long id){
    //     return service.findById(id).orElseThrow();
    // }


    @PostMapping 
    public ResponseEntity<?> create(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user)); // 201
    }
    // @PostMapping 
    // @ResponseStatus(HttpStatus.CREATED)
    // public User create(@RequestBody User user){
        //     return service.save(user);
        // }
        
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id){
        Optional<User> optionalUser = service.update(user, id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(optionalUser.orElseThrow()));
        }
        return ResponseEntity.notFound().build(); // 404 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404 
    }
}
