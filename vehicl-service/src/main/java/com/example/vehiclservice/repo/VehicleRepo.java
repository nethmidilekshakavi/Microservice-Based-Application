package com.example.vehiclservice.repo;

import com.example.vehiclservice.entity.Vehicle_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle_entity,Long> {

}
