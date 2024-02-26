package com.booking_manager.booking.services;

import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IBookingService {
    BookingResponseDto createBooking(BookingRequestDto dto);

    BookingResponseDto getBooking(Long id) throws BadRequestException;

    List<BookingResponseDto> getAllBooking();

    String deleteBooking(Long id) throws BadRequestException;
}
