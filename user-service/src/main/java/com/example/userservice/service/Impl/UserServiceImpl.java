package com.example.userservice.service.Impl;

import com.example.userservice.Dto.UserDto;
import com.example.userservice.Dto.UserWithKey;
import com.example.userservice.Exception.NotFoundException;
import com.example.userservice.entity.userEntity;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

   /* @Override
    public boolean userSave(UserDto userDto) {
        userRepo.save(modelMapper.map(userDto,userEntity.class));
        return true;
    }*/

    @Override
    public boolean userUpdate(Long id, UserDto userDto) {

        userEntity user = userRepo.findById(id).orElse(null);

        if (user != null) {
            user.setName(userDto.getName());
            user.setRole(userDto.getRole());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());

            System.out.println(user);

            userRepo.save(user);

            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean userDelete(Long id) {

        Optional<userEntity> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()){
            userRepo.deleteById(id);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public List<userEntity> getAllUsers() {
        return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDto>>(){}.getType());
    }

    @Override
    public boolean sendCodeToChangePassword(UserWithKey userWithKey) {
        Optional<userEntity> byEmail = userRepo.findByEmail((userWithKey.getEmail()));
        if (byEmail.isPresent()) {
            return true;
        }
        return false;
    }



    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            userEntity user = userRepo.findByEmail(username)
                    .orElseThrow(() -> new NotFoundException("User Name Not Found"));

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
            );
        };
    }
}
