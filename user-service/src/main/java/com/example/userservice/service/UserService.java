package com.example.userservice.service;


import com.example.userservice.Dto.UserDto;

public interface UserService {

    boolean userSave(UserDto userDto);

    boolean userUpdate(Long id,UserDto userDto);
}
