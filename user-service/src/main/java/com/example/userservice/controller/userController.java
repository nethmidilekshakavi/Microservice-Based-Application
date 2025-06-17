package com.example.userservice.controller;


import com.example.userservice.Dto.UserDto;
import com.example.userservice.entity.userEntity;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class userController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/all")
    public List<userEntity> getUser(){
        return userService.getAllUsers();
    }


    @PostMapping("save")
    public ResponseEntity<Void> saveUser(@RequestBody UserDto userDto){

        String setRole = "USER";

        userDto.setRole(setRole);

        System.out.println(userDto);

        boolean save = userService.userSave(userDto);

        if (!save){

          return  new ResponseEntity<>(HttpStatus.OK);

        }else {
             new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PutMapping(value = "update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable ("id") Long id,
                                           @RequestBody UserDto userDto) {
        try {

            boolean save = userService.userUpdate(id, userDto);

            if (save) {
                new ResponseEntity<Void>(HttpStatus.OK);
            }
            new ResponseEntity<Void>(HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable ("id") Long id){

        boolean save = userService.userDelete(id);

        if (save){
            new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("updateRole/{id}")
    public ResponseEntity<Void> updateRole (@PathVariable ("id") Long  id){

        Optional<userEntity> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()){
            userEntity user = optionalUser.get();
            user.setRole("ADMIN");
            userRepo.save(user);
        }else {
            new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
