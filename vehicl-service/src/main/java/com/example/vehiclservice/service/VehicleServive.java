package com.example.vehiclservice.service;

import com.example.vehiclservice.dto.vehicleDto;

public interface VehicleServive {
     boolean registerVehicle(vehicleDto vehicleDto, String authHeader);
     boolean updateVehicle(vehicleDto vehicleDto,Long id,String authHeader);
}
