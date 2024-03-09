package com.booking_manager.availability.services;

import com.booking_manager.availability.models.dtos.BaseResponse;
import com.booking_manager.availability.models.dtos.StayResponseDto;
import com.booking_manager.availability.models.dtos.StayRequestDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IStayService {
    BaseResponse createStay(StayRequestDto dto) throws Exception;
    List<StayResponseDto> getAllStays(Long id) throws BadRequestException, Exception;
    BaseResponse checkAvailabilityByBookingService(StayRequestDto dto);
    BaseResponse deleteStay(Long id);
}
