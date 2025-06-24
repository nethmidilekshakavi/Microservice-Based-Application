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

    @Override
    public boolean savePayment(PaymentDto paymentDto, String authHeader) {
        System.out.println("payment ekata awa");


        System.out.println("user id" + paymentDto.getUserId());

        String user = "http://localhost:8080/user-service/api/v1/user/" + paymentDto.getUserId();
        String vehicle = "http://localhost:8080/vehicle-service/api/v1/vehicle/" + paymentDto.getVehicleId();
        String space = "http://localhost:8080/parking-service/api/v1/parkingSpace/reservationGetId/" + paymentDto.getReservationId();
        String completeStatus = "http://localhost:8082/parking-service/api/v1/parkingSpace/complete/" + paymentDto.getPaymentStatus();

        paymentDto.setPaymentTime(LocalDateTime.now());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<UserDto> response = restTemplate.exchange(
                    user,
                    HttpMethod.GET,
                    entity,
                    UserDto.class
            );

            ResponseEntity<ReservationDto> response2 = restTemplate.exchange(
                    completeStatus,
                    HttpMethod.PUT,
                    entity,
                    ReservationDto.class
            );

            ResponseEntity<ReservationDto> response3 = restTemplate.exchange(
                    space,
                    HttpMethod.GET,
                    entity,
                    ReservationDto.class
            );

            ResponseEntity<ReservationDto> response4 = restTemplate.exchange(
                    vehicle,
                    HttpMethod.GET,
                    entity,
                    ReservationDto.class
            );



            if (response.getStatusCode().is2xxSuccessful() && response3.getStatusCode().is2xxSuccessful()
                    && response2.getStatusCode().is2xxSuccessful() && response4.getStatusCode().is2xxSuccessful()) {

                UserDto userDto = response.getBody();
                ReservationDto reservationDto = response3.getBody();

                if (userDto != null && userDto.isActive()  & reservationDto != null) {
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
    }
}
