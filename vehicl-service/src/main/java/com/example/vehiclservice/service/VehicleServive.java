package com.example.vehiclservice.service;

import com.example.vehiclservice.dto.vehicleDto;
import com.example.vehiclservice.entity.Vehicle_entity;

import java.util.List;

public interface VehicleServive {
     boolean registerVehicle(vehicleDto vehicleDto, String authHeader);
     boolean updateVehicle(vehicleDto vehicleDto,Long id,String authHeader);
     boolean deleteVehicle(Long id,String authHeader);
     List<Vehicle_entity> getAllVehicles();
}
