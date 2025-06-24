package com.example.parkingservice.controller;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.service.Parking_spaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parkingSpace")
public class parking_spaceController {

    @Autowired
    private Parking_spaceService parkingSpaceService;

    @GetMapping("all")
    public String getParking(){
        return "parking service";
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveParkingSpace(
            @RequestBody Parking_spaceDTO parkingSpaceDTO,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println(parkingSpaceDTO);
        boolean success = parkingSpaceService.saveParkingSpace(parkingSpaceDTO, authHeader);
        return success ? ResponseEntity.ok("Parking Space registered") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking Space registration failed");
    }


}
