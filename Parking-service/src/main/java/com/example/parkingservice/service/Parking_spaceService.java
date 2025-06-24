package com.example.parkingservice.service;

import com.example.parkingservice.dto.Parking_spaceDTO;

public interface Parking_spaceService {

    boolean saveParkingSpace(Parking_spaceDTO parkingSpaceDTO,String authHeader);

}
