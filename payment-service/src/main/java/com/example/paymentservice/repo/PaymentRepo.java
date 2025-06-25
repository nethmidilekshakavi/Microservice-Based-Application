package com.example.paymentservice.repo;

import com.example.paymentservice.entity.Payment_Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment_Entity,Long> {
    boolean existsByReservationId(Long reservationId);
    Optional<Payment_Entity> findByPaymentId(Long paymentId);

}
