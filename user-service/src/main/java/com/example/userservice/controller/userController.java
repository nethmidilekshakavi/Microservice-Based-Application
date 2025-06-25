package com.example.userservice.controller;


import com.example.userservice.Dto.UserDto;
import com.example.userservice.entity.userEntity;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.JWTService;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  JWTService jwtService;


  /*  @GetMapping("/all")
    public List<userEntity> getUser(){
        return userService.getAllUsers();
    }*/


//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {

        System.out.println("GET POST");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing Token");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }

        // token is valid → continue to fetch user data
        List<userEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }



    @GetMapping("getUserId/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<userEntity> userOpt = userRepo.findById(id);

        if (userOpt.isPresent()) {
            UserDto dto = modelMapper.map(userOpt.get(), UserDto.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
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

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<userEntity> user = userRepo.findById(id);
        if (user.isPresent()) {
            UserDto userDto = modelMapper.map(user.get(), UserDto.class);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
