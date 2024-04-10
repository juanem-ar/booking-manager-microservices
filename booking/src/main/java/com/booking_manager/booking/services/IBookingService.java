package com.booking_manager.booking.services;

import com.booking_manager.booking.models.dtos.*;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IBookingService {
    BookingResponseDto createBooking(BookingRequestDto dto) throws Exception;
    BookingFullResponseDto getBooking(Long id) throws BadRequestException;
    List<BookingResponseDtoList> getAllBookingByRentalUnit(Long id);
    String deleteBooking(Long id) throws BadRequestException;
    BookingFullResponseDto addGuestToBookingByBookingId(GuestRequestDto dto, Long id) throws BadRequestException;
    String setRealCheckIn(Long id) throws BadRequestException;
    String setRealCheckOut(Long id) throws BadRequestException;
}
