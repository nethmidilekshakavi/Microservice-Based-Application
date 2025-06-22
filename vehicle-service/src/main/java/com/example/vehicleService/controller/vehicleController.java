package com.example.vehicleService.controller;

import com.example.vehicleService.service.VehicleServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vehicle")
public class vehicleController {

    @Autowired
    private VehicleServive vehicleServive;


    @GetMapping("/all")
    public String getVehicle(){
        return "vehicle service";
    }

/*


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
    }*/
}



