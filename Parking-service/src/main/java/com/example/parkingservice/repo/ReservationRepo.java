package com.example.parkingservice.repo;

import com.example.parkingservice.entity.Parking_Space;
import com.example.parkingservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Long> {

}
