package com.example.vehicleService.service.Impl;

import com.example.vehicleService.repo.VehicleRepo;
import com.example.vehicleService.service.VehicleServive;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleServive {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VehicleRepo vehicleRepo;



  /*  @Override
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
    }*/


}
