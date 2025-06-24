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

    @GetMapping("/all")
    public String getVehicle() {
        return "vehicle service";
    }

    @PostMapping("/save")
    public ResponseEntity<?> registerVehicle(
            @RequestBody vehicleDto vehicleDto,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println(vehicleDto);
        boolean success = vehicleServive.registerVehicle(vehicleDto, authHeader);
        return success ? ResponseEntity.ok("Vehicle registered") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle registration failed");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVehicle(@RequestBody vehicleDto vehicleDto,
                                           @PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader){


        boolean success = vehicleServive.updateVehicle(vehicleDto, id,authHeader);
        return success ? ResponseEntity.ok("Vehicle Updated") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle update failed");

    }
}



