package com.example.userservice.controller;

import com.example.userservice.Security.Responce.JWTAuthResponse;
import com.example.userservice.Security.Secure.SignIn;
import com.example.userservice.Security.Secure.SignUp;
import com.example.userservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Login/auth")
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> signIN(@RequestBody SignIn signIn){
        System.out.println("sign in unaaa");
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }
    @PostMapping("/signUp")
    public ResponseEntity<JWTAuthResponse> saveUser(@RequestBody SignUp signUp){
        return ResponseEntity.ok(authenticationService.signUp(signUp));
    }



}




