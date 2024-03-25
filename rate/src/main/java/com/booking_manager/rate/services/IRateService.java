package com.booking_manager.rate.services;

import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;

public interface IRateService {
    RateResponseDto createRate(RateRequestDto dto);

    RateResponseDto getRate(Long id);
}
