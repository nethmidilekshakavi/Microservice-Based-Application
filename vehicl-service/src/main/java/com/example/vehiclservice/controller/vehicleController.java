package com.example.vehiclservice.controller;

import com.example.vehiclservice.dto.vehicleDto;
import com.example.vehiclservice.entity.Vehicle_entity;
import com.example.vehiclservice.repo.VehicleRepo;
import com.example.vehiclservice.service.VehicleServive;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/vehicle")
public class vehicleController {

    @Autowired
    private VehicleServive vehicleServive;
    @Autowired
    private VehicleRepo vehicleRepo;
    @Autowired
    private ModelMapper modelMapper;

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

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader){

        boolean success = vehicleServive.deleteVehicle(id,authHeader);
        return success ? ResponseEntity.ok("Vehicle deleted") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle delete failed");

    }

    @GetMapping("/getAll")
    public List<Vehicle_entity> getAllVehicles(){
        return vehicleServive.getAllVehicles();
    }


    //get Vehicle Id
    @GetMapping("/{id}")
    public ResponseEntity<vehicleDto> getUserById(@PathVariable Long id) {
        Optional<Vehicle_entity> user = vehicleRepo.findById(id);
        if (user.isPresent()) {
            vehicleDto vehicleDto = modelMapper.map(user.get(), vehicleDto.class);
            return ResponseEntity.ok(vehicleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



