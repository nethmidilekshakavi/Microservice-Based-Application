package com.example.vehiclservice.service.Impl;

import com.example.vehiclservice.dto.UserDto;
import com.example.vehiclservice.dto.vehicleDto;
import com.example.vehiclservice.entity.Vehicle_entity;
import com.example.vehiclservice.repo.VehicleRepo;
import com.example.vehiclservice.service.VehicleServive;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
        System.out.println("register ekta awa");
        Optional<Vehicle_entity> existing = vehicleRepo.findByPlateNumber(vehicleDto.getPlateNumber());
        if (existing.isPresent()) {
            return false;
        }

        System.out.println("user id" + vehicleDto.getUserId());

        String url = "http://localhost:8080/user-service/api/v1/user/" + vehicleDto.getUserId();

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
                    vehicle.setUserId(vehicleDto.getUserId());

                    vehicleRepo.save(vehicle);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateVehicle(vehicleDto vehicleDto, Long id,String authHeader) {
        System.out.println("Update ekat awa");

        Optional<Vehicle_entity> existingOpt = vehicleRepo.findByPlateNumber(vehicleDto.getPlateNumber());
        if (existingOpt.isEmpty()) {
            System.out.println("No vehicle found with plate number: " + vehicleDto.getPlateNumber());
            return false;
        }

        Vehicle_entity vehicle = existingOpt.get();

        String url = "http://localhost:8080/user-service/api/v1/user/" + vehicleDto.getUserId();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", String.valueOf(authHeader));
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
                    vehicle.setBrand(vehicleDto.getBrand());
                    vehicle.setModel(vehicleDto.getModel());
                    vehicle.setColor(vehicleDto.getColor());
                    vehicle.setType(vehicleDto.getType());
                    vehicle.setParked(vehicleDto.isParked());
                    vehicle.setUserId(vehicleDto.getUserId());

                    vehicleRepo.save(vehicle);

                    System.out.println("Vehicle updated successfully.");
                    return true;
                } else {
                    System.out.println("User is inactive or not found.");
                    return false;
                }
            } else {
                System.out.println("Failed to contact user-service. Status: " + response.getStatusCode());
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteVehicle(Long id, String authHeader) {
        Optional<Vehicle_entity> optionalUser = vehicleRepo.findById(id);

        if (optionalUser.isPresent()){
            vehicleRepo.deleteById(id);
            System.out.println("Vehicle delete successfully.");
            return true;
        }else {
            System.out.println("Vehicle delete Failed.");
            return false;
        }

    }

    @Override
    public List<Vehicle_entity> getAllVehicles() {
            return modelMapper.map(vehicleRepo.findAll(), new TypeToken<List<vehicleDto>>(){}.getType());
    }


}



