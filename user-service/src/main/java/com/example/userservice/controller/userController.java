package com.example.userservice.controller;


import com.example.userservice.Dto.UserDto;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class userController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/all")
    public String getUser(){
        return "user service";
    }


    @PostMapping("save")
    public ResponseEntity<Void> saveUser(@RequestBody UserDto userDto){

        String setRole = "USER";

        userDto.setRole(setRole);

        System.out.println(userDto);

        boolean save = userService.userSave(userDto);

        if (save){

            new ResponseEntity<Void>(HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
