package com.example.vehiclservice.controller;

import com.example.vehiclservice.dto.vehicleDto;
import com.example.vehiclservice.service.VehicleServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vehicle")
public class vehicleController {

    @Autowired
    private VehicleServive vehicleServive;

    @GetMapping("all")
    public String getVehicles() {
        return "vehicle service";
    }

    @PostMapping("/save")
    public ResponseEntity<Void> registerVehicle(@RequestBody vehicleDto vehicleDto,
                                                @RequestHeader("Authorization") String authHeader) {
        try {
            boolean saved = vehicleServive.RegisterVehicle(vehicleDto, authHeader);
            if (saved) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}



