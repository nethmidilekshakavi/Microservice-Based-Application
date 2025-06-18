package com.example.vehiclservice.service;

import com.example.vehiclservice.dto.vehicleDto;

public interface VehicleServive {
    boolean RegisterVehicle(vehicleDto vehicleDto,String token);
}
