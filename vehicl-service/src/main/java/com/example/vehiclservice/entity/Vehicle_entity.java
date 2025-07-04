package com.example.vehiclservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vehicle_entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(nullable = false)
    private String plateNumber;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private String color;

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    private ParkingStatus status;

    @Column(nullable = false)
    private Long userId;

    public enum ParkingStatus {
        PARKED,
        NOT_PARKED
    }

}
