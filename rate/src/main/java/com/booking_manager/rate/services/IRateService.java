package com.booking_manager.rate.services;

import com.booking_manager.rate.models.dtos.TotalAmountComplexResponse;
import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;

import java.time.LocalDate;

public interface IRateService {
    RateResponseDto createRate(RateRequestDto dto);
    RateResponseDto getRate(Long id);
    String deleteRate(Long id);
    TotalAmountComplexResponse getRateByStay(Long businessUnitId, Long rentalUnitId, LocalDate checkIn, LocalDate checkOut);
    RateResponseDto editRate(Long id, RateRequestDto dto);
}
