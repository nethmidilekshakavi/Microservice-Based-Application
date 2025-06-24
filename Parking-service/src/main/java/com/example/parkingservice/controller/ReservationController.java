package com.example.parkingservice.controller;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.dto.ReservationDto;
import com.example.parkingservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("api/v1/parkingSpace")
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("reservation/save")
    public ResponseEntity<?> saveReservation(
            @RequestBody ReservationDto reservationDto,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println(reservationDto);
        boolean success = reservationService.saveReservation(reservationDto, authHeader);
        return success ? ResponseEntity.ok("Reservation saved") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation failed: No available parking space.");
    }

}
