package com.example.paymentservice.repo;

import com.example.paymentservice.entity.Payment_Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment_Entity,Long> {
}
