package com.example.parkingservice.service.impl;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.dto.UserDto;
import com.example.parkingservice.entity.Parking_Space;
import com.example.parkingservice.repo.ParkingRepo;
import com.example.parkingservice.service.Parking_spaceService;
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
public class Parking_SpaceServiceImpl implements Parking_spaceService {

    @Autowired
    private ParkingRepo parkingRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public boolean updateSpace(Parking_spaceDTO parkingSpaceDTO, Long id, String authHeader) {
        System.out.println("Update ekat awa");

        Optional<Parking_Space> optionalSpace = parkingRepo.findById(id);
        if (optionalSpace.isEmpty()) {
            System.out.println("Parking space not found with ID: " + id);
            return false;
        }

        Parking_Space space = optionalSpace.get();


        String url = "http://localhost:8080/user-service/api/v1/user/" + parkingSpaceDTO.getUserId();

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

    @Override
    public boolean deleteSpace(Long id, String authHeader) {
        Optional<Parking_Space> optionalUser = parkingRepo.findById(id);

        if (optionalUser.isPresent()){
            parkingRepo.deleteById(id);
            System.out.println("space delete successfully.");
            return true;
        }else {
            System.out.println("space delete Failed.");
            return false;
        }

    }

    @Override
    public List<Parking_Space> getAllSpaces() {
        return modelMapper.map(parkingRepo.findAll(), new TypeToken<List<Parking_spaceDTO>>(){}.getType());
    }
}
