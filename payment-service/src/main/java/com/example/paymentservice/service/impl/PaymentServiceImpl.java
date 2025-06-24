package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.PaymentDto;
import com.example.paymentservice.dto.ReservationDto;
import com.example.paymentservice.dto.UserDto;
import com.example.paymentservice.dto.vehicleDto;
import com.example.paymentservice.entity.Payment_Entity;
import com.example.paymentservice.repo.PaymentRepo;
import com.example.paymentservice.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentRepo paymentRepo;

   /* @Override
    public boolean savePayment(PaymentDto paymentDto, String authHeader) {
        System.out.println("payment ekata awa");


        System.out.println("user id" + paymentDto.getUserId());

        String user = "http://localhost:8080/parking-service/api/v1/parkingSpace/userIdByReservation/" + paymentDto.getReservationId();
        String vehicle = "http://localhost:8080/parking-service/api/v1/parkingSpace/vehicleIdByReservation/" + paymentDto.getReservationId();
        String space = "http://localhost:8080/parking-service/api/v1/parkingSpace/reservationGetId/" + paymentDto.getReservationId();
        String TotalAmount = "http://localhost:8080/parking-service/api/v1/parkingSpace/reservationGetId/getAmount/" + paymentDto.getReservationId();

        paymentDto.setPaymentTime(LocalDateTime.now());

        System.out.println("wghdhysgdhsjfhdsjkfjdk--------------------");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Long> response = restTemplate.exchange(
                    user,
                    HttpMethod.GET,
                    entity,
                    Long.class
            );



            ResponseEntity<Long> response3 = restTemplate.exchange(
                    space,
                    HttpMethod.GET,
                    entity,
                    Long.class
            );

            ResponseEntity<Long> response4 = restTemplate.exchange(
                    vehicle,
                    HttpMethod.GET,
                    entity,
                    Long.class
            );

            ResponseEntity<Double> response5 = restTemplate.exchange(
                    TotalAmount,
                    HttpMethod.GET,
                    entity,
                    Double.class
            );



            if (response.getStatusCode().is2xxSuccessful() && response3.getStatusCode().is2xxSuccessful()
                     && response4.getStatusCode().is2xxSuccessful()
                    && response5.getStatusCode().is2xxSuccessful()
            ) {


                Long reservationDto = response3.getBody();

                if (reservationDto != null) {
                    Payment_Entity payment = new Payment_Entity();
                    payment.setPaymentMethod(paymentDto.getPaymentMethod());
                    payment.setPaymentStatus(paymentDto.getPaymentStatus());
                    payment.setPaymentTime(paymentDto.getPaymentTime());
                    payment.setAmount(paymentDto.getAmount());
                    payment.setReservationId(paymentDto.getReservationId());
                    payment.setVehicleId(paymentDto.getVehicleId());
                    payment.setUserId(paymentDto.getUserId());
                    System.out.println(payment);
                    paymentRepo.save(payment);

                    System.out.println(payment);

                    Optional<Payment_Entity> optionalPaymentEntity = paymentRepo.findById(Long.valueOf(paymentDto.getPaymentStatus()));
                    if (optionalPaymentEntity.isPresent()) {
                        Payment_Entity status = optionalPaymentEntity.get();
                        status.setPaymentStatus("SUCCESS");
                        paymentRepo.save(status);
                    }

                    ReservationDto re = response4.getBody();
                    System.out.println("status update: " + re);

                    re.setStatus("COMPLETED");

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    @Override
    public boolean savePayment(PaymentDto paymentDto, String authHeader) {
        System.out.println("payment ekata awa");

        paymentDto.setPaymentTime(LocalDateTime.now());

        String userUrl = "http://localhost:8080/parking-service/api/v1/parkingSpace/userIdByReservation/" + paymentDto.getReservationId();
        String vehicleUrl = "http://localhost:8080/parking-service/api/v1/parkingSpace/vehicleIdByReservation/" + paymentDto.getReservationId();
        String reservationUrl = "http://localhost:8080/parking-service/api/v1/parkingSpace/reservationGetId/" + paymentDto.getReservationId();
        String amountUrl = "http://localhost:8080/parking-service/api/v1/parkingSpace/reservationGetId/getAmount/" + paymentDto.getReservationId();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Long> userResponse = restTemplate.exchange(userUrl, HttpMethod.GET, entity, Long.class);
            Long userId = userResponse.getBody();

            ResponseEntity<Long> vehicleResponse = restTemplate.exchange(vehicleUrl, HttpMethod.GET, entity, Long.class);
            Long vehicleId = vehicleResponse.getBody();

            ResponseEntity<ReservationDto> reservationResponse = restTemplate.exchange(reservationUrl, HttpMethod.GET, entity, ReservationDto.class);
            ReservationDto reservationDto = reservationResponse.getBody();

            ResponseEntity<Double> amountResponse = restTemplate.exchange(amountUrl, HttpMethod.GET, entity, Double.class);
            Double totalAmount = amountResponse.getBody();

            if (userId != null && vehicleId != null && reservationDto != null && totalAmount != null) {
                Payment_Entity payment = new Payment_Entity();
                payment.setUserId(userId);
                payment.setVehicleId(vehicleId);
                payment.setReservationId(paymentDto.getReservationId());
                payment.setAmount(totalAmount);
                payment.setPaymentMethod(paymentDto.getPaymentMethod());
                payment.setPaymentTime(paymentDto.getPaymentTime());
                payment.setPaymentStatus("SUCCESS");

                paymentRepo.save(payment);

                String completeStatusUrl = "http://localhost:8080/parking-service/api/v1/parkingSpace/complete/" + paymentDto.getReservationId();

                restTemplate.exchange(
                        completeStatusUrl,
                        HttpMethod.PUT,
                        entity, // contains Authorization header
                        String.class
                );


                System.out.println("Payment saved successfully!");
                return true;
            } else {
                System.out.println("Missing required data for saving payment");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
