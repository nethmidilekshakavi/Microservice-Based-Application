package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentDto;
import com.example.paymentservice.dto.ReceiptDto;
import com.example.paymentservice.entity.Payment_Entity;
import com.example.paymentservice.repo.PaymentRepo;
import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.util.ReceiptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/payment")
public class paymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepo paymentRepo;


    @GetMapping("all")
    public String getPayment() {
        return "payment service";
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePayment(
            @RequestBody PaymentDto paymentDto,
            @RequestHeader("Authorization") String authHeader) {

        System.out.println(paymentDto);

        if (paymentRepo.existsByReservationId(paymentDto.getReservationId())) {
            System.out.println("Can't save payment");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment already exists for this reservation");
        }

        boolean success = paymentService.savePayment(paymentDto, authHeader);

        return success
                ? ResponseEntity.ok("Payment Successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
    }



    @GetMapping("receipt/{paymentId}")
    /*@PreAuthorize("hasRole('USER')")*/
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long paymentId) {
        Optional<Payment_Entity> tx = paymentRepo.findByPaymentId(paymentId);

        if (tx.isPresent()) {
            byte[] pdf = ReceiptGenerator.generate(tx.get());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=receipt_" + paymentId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}




}
