package com.example.parkingservice.service;

import com.example.parkingservice.dto.ReservationDto;

public interface ReservationService {

    boolean saveReservation(ReservationDto reservationDto, String authHeader);


}
