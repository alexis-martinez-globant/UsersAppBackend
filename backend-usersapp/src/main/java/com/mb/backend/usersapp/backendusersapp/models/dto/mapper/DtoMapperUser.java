package com.mb.backend.usersapp.backendusersapp.models.dto.mapper;

import com.mb.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.mb.backend.usersapp.backendusersapp.models.entities.User;

public class DtoMapperUser {
    
    
    private User user;

    private DtoMapperUser(){

    }
    public static DtoMapperUser builder(){
        return new DtoMapperUser();
    }

    public DtoMapperUser setUser(User user){
        this.user = user;
        return this;
    }
    
    public UserDto build(){
        if (user == null) {
            throw new RuntimeException("Needs the user entity");
        }
        return new UserDto(this.user.getId(), this.user.getUsername(), this.user.getEmail());
    }
}
