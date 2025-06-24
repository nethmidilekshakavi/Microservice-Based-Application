package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentDto;
import com.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
public class paymentController {

    @Autowired
    private PaymentService paymentService;


    @GetMapping("all")
    public String getPayment(){
        return "payment service";
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePayment(
            @RequestBody PaymentDto paymentDto,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println(paymentDto);
        boolean success = paymentService.savePayment(paymentDto, authHeader);
        return success ? ResponseEntity.ok("Payment Successfully") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
    }



}
