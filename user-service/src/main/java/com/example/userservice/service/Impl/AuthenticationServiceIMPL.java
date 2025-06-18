package com.example.userservice.service.Impl;

import com.example.userservice.Dto.PasswordDto;
import com.example.userservice.Dto.UserDto;
import com.example.userservice.Exception.NotFoundException;
import com.example.userservice.Security.Responce.JWTAuthResponse;
import com.example.userservice.Security.Secure.SignIn;
import com.example.userservice.Security.Secure.SignUp;
import com.example.userservice.entity.userEntity;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.AuthenticationService;
import com.example.userservice.service.JWTService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceIMPL implements AuthenticationService {

    private final ModelMapper modelMapper;
    private final UserRepo userDao;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public static String userRoleget = "";
    public static int userIDget = 0;
    public static String  UserNameGet = "";
    public static String  ProPic = "";

    @Override
    public JWTAuthResponse signUp(SignUp signUp) {
        UserDto userDTO = UserDto.builder()
                .email(signUp.getEmail())
                .name(signUp.getName())
                .role(signUp.getRole())
                .image(signUp.getImage())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .build();

        userEntity userEntity1 = modelMapper.map(userDTO, userEntity.class);
        userDao.save(userEntity1);

        String generateToken = jwtService.generateToken(userEntity1);

        return JWTAuthResponse.builder()
                .tokens(generateToken)
                .build();
    }

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword())
        );

        userEntity userEntity = userDao.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userEntity.getRole());
        claims.put("userId", userEntity.getUserId());
        claims.put("name", userEntity.getName());
        claims.put("email" ,userEntity.getEmail());

        String generateToken = jwtService.generateToken(claims, userEntity.getEmail());
        System.out.println("==================================  :" + generateToken);

        userRoleget = String.valueOf(userEntity.getRole());
        userIDget = Math.toIntExact(userEntity.getUserId());

        String profilePicBase64 = null;
        if (userEntity.getImage() != null && userEntity.getImage().length() > 0) {
            byte[] profilePicBytes = userEntity.getImage().getBytes();
            profilePicBase64 = Base64.getEncoder().encodeToString(profilePicBytes);
        }
        ProPic = profilePicBase64;

        return JWTAuthResponse.builder()
                .tokens(generateToken)
                .name(userEntity.getName())
                .userId(userIDget)
                .role(userRoleget)
                .proPic(ProPic)
                .build();
    }



    @Override
        public String validateToken(String token) {
        System.out.println("validate");
        try {
            return jwtService.extractUserName(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JWTAuthResponse refreshToken(String refreshToken) {
        String user =jwtService.extractUserName(refreshToken);
        userEntity findUser =userDao.findByEmail(user).orElseThrow(()-> new NotFoundException("Couldn't find User"));
        String token =jwtService.refreshToken( findUser);
        return JWTAuthResponse.builder().tokens(token).build();
    }

    @Override
    public void changePassword(PasswordDto passwordDto) {
        Optional<userEntity> byEmail = userDao.findByEmail(passwordDto.getEmail());
        if (byEmail.isPresent()) {
            userEntity userEntity = userDao.getReferenceById(byEmail.get().getUserId());
            userEntity.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userDao.save(userEntity);
        }
    }
    public String getSignedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new IllegalStateException("No authenticated user found");
    }




}