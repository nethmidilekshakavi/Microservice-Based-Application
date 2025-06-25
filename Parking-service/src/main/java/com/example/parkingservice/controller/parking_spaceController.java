package com.example.parkingservice.controller;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.entity.Parking_Space;
import com.example.parkingservice.entity.Reservation;
import com.example.parkingservice.repo.ParkingRepo;
import com.example.parkingservice.service.Parking_spaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/parkingSpace")
public class parking_spaceController {

    @Autowired
    private Parking_spaceService parkingSpaceService;

    @Autowired
    private ParkingRepo parkingRepo;

    @Autowired
    private ModelMapper modelMapper;

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

    @PutMapping("/Available/{id}")
    public ResponseEntity<?> updateStatusAvailable(@PathVariable Long id) {
        Optional<Parking_Space> optional = parkingRepo.findById(id);
        if (optional.isPresent()) {
            Parking_Space space = optional.get();
            space.setAvailable(true);
            parkingRepo.save(space);
            return ResponseEntity.ok("Status updated to Available");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking space not found");
        }
    }


    @PutMapping("/NotAvailable/{id}")
    public ResponseEntity<?> updateStatusNotAvailable(@PathVariable Long id) {
        Optional<Parking_Space> optional = parkingRepo.findById(id);
        if (optional.isPresent()) {
            Parking_Space space = optional.get();
            space.setAvailable(false);
            parkingRepo.save(space);
            return ResponseEntity.ok("Status updated to Not_Available");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking space not found");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Parking_spaceDTO> getSpaceById(@PathVariable Long id) {
        Optional<Parking_Space> user = parkingRepo.findById(id);
        if (user.isPresent()) {
            Parking_spaceDTO parkingSpaceDTO = modelMapper.map(user.get(), Parking_spaceDTO.class);
            return ResponseEntity.ok(parkingSpaceDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
