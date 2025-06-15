package com.example.parkingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Parking_Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;

    private String location; // ස්ථානය
    private boolean isAvailable; // ලබාගත හැකිද?
    private String zone; // නාගරික කලාපය (Zone A, B වගේ)

/*
    @ManyToMany
    @JoinColumn(name = "owner_id")
    private user_entity userEntity;
*/

}
