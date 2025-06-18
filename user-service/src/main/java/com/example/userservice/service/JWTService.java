package com.example.userservice.service;

import com.example.userservice.entity.userEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;


public interface JWTService {
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    //String generateToken(UserDetails userDetails);
    String refreshToken(UserDetails userDetails);
    boolean validateToken(String token);
    String generateToken(userEntity user);
    String generateToken(Map<String, Object> claims, String subject);
}
