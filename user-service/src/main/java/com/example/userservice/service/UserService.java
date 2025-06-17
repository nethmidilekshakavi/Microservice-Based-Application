package com.example.userservice.service;


import com.example.userservice.Dto.UserDto;
import com.example.userservice.entity.userEntity;

import java.util.List;

public interface UserService {

    boolean userSave(UserDto userDto);

    boolean userUpdate(Long id,UserDto userDto);

    boolean userDelete(Long id);

    List<userEntity> getAllUsers();
}
