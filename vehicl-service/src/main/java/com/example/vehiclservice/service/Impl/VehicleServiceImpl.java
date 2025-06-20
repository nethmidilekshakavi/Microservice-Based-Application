package com.example.vehiclservice.service.Impl;

import com.example.userservice.Dto.UserDto;
import com.example.vehiclservice.dto.vehicleDto;
import com.example.vehiclservice.entity.Vehicle_entity;
import com.example.vehiclservice.repo.VehicleRepo;
import com.example.vehiclservice.service.VehicleServive;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleServive {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean RegisterVehicle(vehicleDto vehicleDto, String token) {
        // Prepare authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Validate user by calling user-service
        ResponseEntity<UserDto> response = restTemplate.exchange(
                "http://localhost:8084/user-service/api/v1/user/getUserId/" + vehicleDto.getUserId(),
                HttpMethod.GET,
                entity,
                UserDto.class
        );

        System.out.println(response);


        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Vehicle_entity vehicle = modelMapper.map(vehicleDto, Vehicle_entity.class);
            vehicleRepo.save(vehicle);
            return true;
        }

        return true;
    }


}
