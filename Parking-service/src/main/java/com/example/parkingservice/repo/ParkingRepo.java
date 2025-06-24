package com.example.parkingservice.repo;

import com.example.parkingservice.entity.Parking_Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepo extends JpaRepository<Parking_Space,Long> {
}
