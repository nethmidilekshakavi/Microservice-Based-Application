package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentDto;

public interface PaymentService {
    boolean savePayment(PaymentDto paymentDto, String authHeader);
}
