package com.example.parkingservice.controller;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.dto.ReservationDto;
import com.example.parkingservice.dto.vehicleDto;
import com.example.parkingservice.entity.Reservation;
import com.example.parkingservice.repo.ReservationRepo;
import com.example.parkingservice.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RequestMapping("api/v1/parkingSpace")
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("reservation/save")
    public ResponseEntity<?> saveReservation(
            @RequestBody ReservationDto reservationDto,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println(reservationDto);
        boolean success = reservationService.saveReservation(reservationDto, authHeader);
        return success ? ResponseEntity.ok("Reservation saved") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation failed: No available parking space.");
    }

    @GetMapping("reservationGetId/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationRepo.findById(id);
        if (reservation.isPresent()) {
            ReservationDto reservationDto = modelMapper.map(reservation.get(), ReservationDto.class);
            return ResponseEntity.ok(reservationDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id) {
        Optional<Reservation> optional = reservationRepo.findById(id);
        if (optional.isPresent()) {
            Reservation reservation = optional.get();
            reservation.setStatus(Reservation.Status.COMPLETED);
            reservationRepo.save(reservation);
            return ResponseEntity.ok("Reservation status Updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }
    }


    @GetMapping("reservationGetId/getAmount/{id}")
    public ResponseEntity<Double> createAmount(@PathVariable  Long id,@RequestBody ReservationDto reservationDto,
                                               @RequestHeader("Authorization") String authHeader) {

        double amount = reservationService.calculateAmount(id, reservationDto, authHeader);
        if (amount == 0.0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0.0);
        } else {
            return ResponseEntity.ok(amount);
        }
    }
}
