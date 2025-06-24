package com.example.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class vehicleDto {
    private String plateNumber;
    private String brand;
    private String model;
    private String color;
    private String type;
    private boolean isParked;
    private String entryTime;
    private String exitTime;
    private Long userId;
}
