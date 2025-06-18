package com.example.userservice.service;


import com.example.userservice.Dto.PasswordDto;
import com.example.userservice.Security.Responce.JWTAuthResponse;
import com.example.userservice.Security.Secure.SignIn;
import com.example.userservice.Security.Secure.SignUp;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService {
    JWTAuthResponse signUp(SignUp signUp);
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse refreshToken(String refreshToken);
    String validateToken(String token);
    void changePassword(PasswordDto passwordDto);

}
