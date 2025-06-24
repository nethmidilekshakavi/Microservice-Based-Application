package com.example.parkingservice.service.impl;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.dto.UserDto;
import com.example.parkingservice.entity.Parking_Space;
import com.example.parkingservice.repo.ParkingRepo;
import com.example.parkingservice.service.Parking_spaceService;
import jakarta.transaction.Transactional;
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
public class Parking_SpaceServiceImpl implements Parking_spaceService {

    @Autowired
    private ParkingRepo parkingRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean saveParkingSpace(Parking_spaceDTO parkingSpaceDTO,String authHeader) {
        System.out.println("space skata awa");

        System.out.println("parking id" + parkingSpaceDTO.getUserId());

        String url = "http://localhost:8080/user-service/api/v1/user/" + parkingSpaceDTO.getUserId();

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
                    Parking_Space space = new Parking_Space();
                    space.setAvailable(parkingSpaceDTO.isAvailable());
                    space.setZone(parkingSpaceDTO.getZone());
                    space.setLocation(parkingSpaceDTO.getLocation());
                    space.setUserId(parkingSpaceDTO.getUserId());
                    parkingRepo.save(space);
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
}
