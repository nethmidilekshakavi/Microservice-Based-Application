package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentDto;
import com.example.paymentservice.dto.ReceiptDto;
import com.example.paymentservice.entity.Payment_Entity;

import java.util.Map;
import java.util.Optional;

public interface PaymentService {
    boolean savePayment(PaymentDto paymentDto, String authHeader);
    Optional<Payment_Entity> getTransactionById(Long id);
}
