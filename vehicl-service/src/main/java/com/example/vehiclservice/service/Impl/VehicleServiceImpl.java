package com.example.vehiclservice.service.Impl;

import com.example.vehiclservice.dto.UserDto;
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

    public boolean registerVehicle(vehicleDto vehicleDto, String authHeader) {
        System.out.println("service " + vehicleDto);
        Optional<Vehicle_entity> existing = vehicleRepo.findByPlateNumber(vehicleDto.getPlateNumber());
        if (existing.isPresent()) {
            System.out.println("Vehicle already exists with plate: " + vehicleDto.getPlateNumber());
            return false;
        }

        System.out.println("user id: " + vehicleDto.getUserId());

        String url = "http://localhost:8080/user-service/api/v1/user/" + vehicleDto.getUserId();
        System.out.println("Calling URL: " + url);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<UserDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    UserDto.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                UserDto userDto = response.getBody();

                if (userDto != null && userDto.isActive()) {
                    Vehicle_entity vehicle = new Vehicle_entity();
                    vehicle.setPlateNumber(vehicleDto.getPlateNumber());
                    vehicle.setBrand(vehicleDto.getBrand());
                    vehicle.setModel(vehicleDto.getModel());
                    vehicle.setColor(vehicleDto.getColor());
                    vehicle.setType(vehicleDto.getType());
                    vehicle.setParked(vehicleDto.isParked());
                    vehicle.setEntryTime(vehicleDto.getEntryTime());
                    vehicle.setExitTime(vehicleDto.getExitTime());
                    vehicle.setUserId(vehicleDto.getUserId());

                    System.out.println("Mapped Vehicle Entity: " + vehicle);

                    vehicleRepo.save(vehicle); // Hibernate will auto-handle version

                    System.out.println("Vehicle registered successfully for active user.");
                    return true;
                } else {
                    System.out.println("User is not active. Vehicle not registered.");
                    return false;
                }
            } else {
                System.out.println("User service responded with non-success status");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Debug properly
            System.err.println("Error calling user-service: " + e.getMessage());
            return false;
        }
    }



}



