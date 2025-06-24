package com.example.parkingservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reservation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // "ACTIVE", "COMPLETED", "CANCELLED"

    private boolean parkingSpace;

    private Long vehicleId;
    private Long userId;


}
