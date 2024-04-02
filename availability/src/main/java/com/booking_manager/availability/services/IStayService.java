package com.booking_manager.availability.services;

import com.booking_manager.availability.models.dtos.BaseResponse;
import com.booking_manager.availability.models.dtos.StayResponseDto;
import com.booking_manager.availability.models.dtos.StayRequestDto;

import java.util.List;

public interface IStayService {
    BaseResponse createStay(StayRequestDto dto);
    List<StayResponseDto> getAllStaysByRentalUnitId(Long id) throws Exception;
    BaseResponse checkAvailabilityByBookingService(StayRequestDto dto);
    BaseResponse deleteStay(Long id);
    BaseResponse deleteStayById(Long id);
    List<StayResponseDto>getAllStaysByBookingId(Long id) throws Exception;
}
