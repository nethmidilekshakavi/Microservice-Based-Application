package com.example.parkingservice.controller;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.entity.Parking_Space;
import com.example.parkingservice.repo.ParkingRepo;
import com.example.parkingservice.service.Parking_spaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/parkingSpace")
public class parking_spaceController {

    @Autowired
    private Parking_spaceService parkingSpaceService;

    @Autowired
    private ParkingRepo parkingRepo;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateParkingSpace(@RequestBody Parking_spaceDTO parkingSpaceDTO,
                                           @PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader){

        boolean success = parkingSpaceService.updateSpace(parkingSpaceDTO, id,authHeader);
        return success ? ResponseEntity.ok("space Updated") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("space update failed");

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteParkingSpace(@PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader){

        boolean success = parkingSpaceService.deleteSpace(id,authHeader);
        return success ? ResponseEntity.ok("ParkingSpace deleted") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("space delete failed");

    }

    @GetMapping("/getAll")
    public List<Parking_Space> getAllSpaces(){
        return parkingSpaceService.getAllSpaces();
    }

    @GetMapping("/available")
    public ResponseEntity<List<Parking_Space>> getAvailableSpaces() {
        List<Parking_Space> availableSpaces = parkingRepo.findAllByIsAvailableTrue();
        return ResponseEntity.ok(availableSpaces);
    }

}
