package com.example.vehiclservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vehicle")
public class vehicleController {

    @GetMapping("all")

    public String getVehicles(){
        return "vehicle service";
    }



}
