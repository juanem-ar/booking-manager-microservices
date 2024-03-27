package com.booking_manager.rate.services;

import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;

import java.time.LocalDate;

public interface IRateService {
    RateResponseDto createRate(RateRequestDto dto);
    RateResponseDto getRate(Long id);
    String deleteRate(Long id);
    Double getRateByStay(Long businessUnitId, Long rentalUnitId, LocalDate checkIn, LocalDate checkOut);
}
