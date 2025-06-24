package com.example.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private Double amount; // මුදල
    private String paymentMethod; // කාඩ් / QR / Mock
    private String paymentStatus; // "SUCCESS", "FAILED", "PENDING"
    private LocalDateTime paymentTime; // ගෙවීමේ වේලාව
    private Long UserId;
    private Long ReservationId;
    private Long vehicleId;

}
