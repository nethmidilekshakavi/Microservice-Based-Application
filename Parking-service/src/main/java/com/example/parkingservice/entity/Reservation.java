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

    private LocalDateTime startTime; // වෙන්කළ ආරම්භ වේලාව
    private LocalDateTime endTime; // අවසන් වේලාව
    private String status; // "ACTIVE", "COMPLETED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Parking_Space parkingSpace;

/*    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private ve;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/


}
