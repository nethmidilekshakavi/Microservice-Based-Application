package com.example.vehiclservice.repo;

import com.example.vehiclservice.entity.Vehicle_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle_entity,Long> {
    Optional<Vehicle_entity> findByPlateNumber(String plateNumber);

}
