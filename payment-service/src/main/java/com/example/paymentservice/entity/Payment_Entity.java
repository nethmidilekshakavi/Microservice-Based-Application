package com.example.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Double amount; // මුදල
    private String paymentMethod; // කාඩ් / QR / Mock
    private String paymentStatus; // "SUCCESS", "FAILED", "PENDING"
    private LocalDateTime paymentTime;
    private Long UserId;
    private Long ReservationId;
    private Long vehicleId;
}
