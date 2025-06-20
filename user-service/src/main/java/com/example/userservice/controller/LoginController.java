package com.example.userservice.controller;

import com.example.userservice.Security.Responce.JWTAuthResponse;
import com.example.userservice.Security.Secure.SignIn;
import com.example.userservice.Security.Secure.SignUp;
import com.example.userservice.Util.PicEncoder;
import com.example.userservice.service.AuthenticationService;
import com.example.userservice.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/Login/auth")
@CrossOrigin
public class LoginController {


    @Autowired
    JWTService jwtService;

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping(value = "/signIn")
    public ResponseEntity<JWTAuthResponse> signIN(@RequestBody SignIn signIn, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("Login Request with Authorization header: " + authHeader);
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }




    @PostMapping(value = "/signUp",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(@RequestPart ("name") String name,
                                                    @RequestPart ("email") String email,
                                                    @RequestPart ("password") String password,
                                                    @RequestPart("image") MultipartFile imageFile

                                                    ){

        String image = PicEncoder.generatePicture(imageFile);

        SignUp signUp = new SignUp();

        signUp.setName(name);
        signUp.setRole("USER");
        signUp.setEmail(email);
        signUp.setImage(image);
        signUp.setPassword(password);

        return ResponseEntity.ok(authenticationService.signUp(signUp));
    }



}




