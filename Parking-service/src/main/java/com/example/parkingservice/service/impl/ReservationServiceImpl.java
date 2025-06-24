package com.example.parkingservice.service.impl;

import com.example.parkingservice.dto.Parking_spaceDTO;
import com.example.parkingservice.dto.ReservationDto;
import com.example.parkingservice.dto.UserDto;
import com.example.parkingservice.dto.vehicleDto;
import com.example.parkingservice.entity.Parking_Space;
import com.example.parkingservice.entity.Reservation;
import com.example.parkingservice.repo.ParkingRepo;
import com.example.parkingservice.repo.ReservationRepo;
import com.example.parkingservice.service.ReservationService;
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

@Transactional
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ParkingRepo parkingRepo;
    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public boolean saveReservation(ReservationDto reservationDto, String authHeader) {

        System.out.println("reservation ekata awa");
        List<Parking_Space> existing = parkingRepo.findAllByIsAvailableTrue();
        System.out.println("--------------------------"+existing);
        reservationDto.setStartTime(LocalDateTime.now());
        reservationDto.setEndTime(LocalDateTime.now());
        System.out.println("start time -  "+reservationDto.getStartTime());
        System.out.println("end time -  "+reservationDto.getEndTime());
        if (existing.isEmpty()) {
            System.out.println("Not Available");
            return false;
        }

        System.out.println("user id" + reservationDto.getUserId());

        String user = "http://localhost:8080/user-service/api/v1/user/" + reservationDto.getUserId();
        String vehicle = "http://localhost:8080/vehicle-service/api/v1/vehicle/" + reservationDto.getVehicleId();
        String space = "http://localhost:8080/parking-service/api/v1/parkingSpace/" + reservationDto.getSpaceId();


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
            ResponseEntity<vehicleDto> response2 = restTemplate.exchange(
                    vehicle,
                    HttpMethod.GET,
                    entity,
                    vehicleDto.class
            );

            ResponseEntity<Parking_spaceDTO> response3 = restTemplate.exchange(
                    space,
                    HttpMethod.GET,
                    entity,
                    Parking_spaceDTO.class
            );


            if (response.getStatusCode().is2xxSuccessful()) {
                UserDto userDto = response.getBody();
                Parking_spaceDTO parkingSpaceDTO = response3.getBody();

                if (userDto != null && userDto.isActive()  & parkingSpaceDTO != null && parkingSpaceDTO.isAvailable()) {
                    Reservation reservation = new Reservation();
                    reservation.setEndTime(reservationDto.getEndTime());
                    reservation.setStartTime(reservationDto.getStartTime());
                    reservation.setStatus(Reservation.Status.valueOf(reservationDto.getStatus()));
                    reservation.setSpaceId(reservationDto.getSpaceId());
                    reservation.setUserId(reservationDto.getUserId());
                    reservation.setVehicleId(reservationDto.getVehicleId());
                    System.out.println(reservation);
                    reservationRepo.save(reservation);

                    //update karanwa space eka  No available parking space
                    Optional<Parking_Space> optionalParkingSpace = parkingRepo.findById(reservationDto.getSpaceId());
                    if (optionalParkingSpace.isPresent()) {
                        Parking_Space parkingSpace = optionalParkingSpace.get();
                        parkingSpace.setAvailable(false);
                        parkingRepo.save(parkingSpace);
                    }


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
