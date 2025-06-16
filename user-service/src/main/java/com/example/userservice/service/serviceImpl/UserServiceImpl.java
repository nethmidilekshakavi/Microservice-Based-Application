package com.example.userservice.service.serviceImpl;

import com.example.userservice.Dto.UserDto;
import com.example.userservice.entity.userEntity;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public boolean userSave(UserDto userDto) {

        userEntity user = userRepo.findById(userDto.getUserId()).orElse(null);

        if (user != null){

           userRepo.save(modelMapper.map(userDto,userEntity.class));
           return true;
        }else {
            return false;
        }

    }
}
