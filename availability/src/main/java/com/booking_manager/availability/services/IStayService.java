package com.booking_manager.availability.services;

import com.booking_manager.availability.models.dtos.*;

import java.util.List;

public interface IStayService {
    BaseResponse createStay(StayRequestDto dto);
    List<StayResponseDto> getAllStaysByRentalUnitId(Long id) throws Exception;
    BaseResponse checkAvailabilityByBookingService(StayRequestDto dto);
    BaseResponse deleteStay(Long id);
    BaseResponse deleteStayById(Long id);
    StayComplexResponseByGet getStayByBookingId(Long id);
}
