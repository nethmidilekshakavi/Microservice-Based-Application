package com.example.parkingservice.service;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.entity.Parking_Space;

import java.util.List;

public interface Parking_spaceService {

    boolean saveParkingSpace(Parking_spaceDTO parkingSpaceDTO,String authHeader);
    boolean updateSpace(Parking_spaceDTO parkingSpaceDTO,Long id,String authHeader);
    boolean deleteSpace(Long id,String authHeader);
    List<Parking_Space> getAllSpaces();

}
