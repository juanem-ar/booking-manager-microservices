package com.booking_manager.booking.services;

import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDto;
import com.booking_manager.booking.models.dtos.BookingResponseDtoList;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IBookingService {
    BookingResponseDto createBooking(BookingRequestDto dto) throws Exception;
    BookingResponseDto getBooking(Long id) throws BadRequestException;
    List<BookingResponseDtoList> getAllBooking();
    String deleteBooking(Long id) throws BadRequestException;
}
