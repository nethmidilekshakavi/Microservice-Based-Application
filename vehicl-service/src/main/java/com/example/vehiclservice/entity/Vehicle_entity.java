package com.example.vehiclservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    private String vehicleNumber; // වාහන අංකය
    private String type; // වාහනයේ වර්ගය (Car, Van, Bike වැනි)

////    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private  user; // වාහනයට අයත් පරිශීලකයා
}
