package com.mb.backend.usersapp.backendusersapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mb.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.mb.backend.usersapp.backendusersapp.models.entities.User;
import com.mb.backend.usersapp.backendusersapp.models.entities.UserRequest;
import com.mb.backend.usersapp.backendusersapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDto> list(){
        return service.findAll();
    };
    
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
       Optional<UserDto> userOptional = service.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build(); // 404 
    }

    @PostMapping 
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
       if (result.hasErrors()) {
        return validation(result);
       }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user)); // 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<UserDto> optionalUser = service.update(user, id);

        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.orElseThrow());
        }
        return ResponseEntity.notFound().build(); // 404 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        Optional<UserDto> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404 
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The input " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


}
