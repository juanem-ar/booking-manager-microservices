package com.booking_manager.booking.services;

import com.booking_manager.booking.models.dtos.BookingFullResponseDto;
import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDto;
import com.booking_manager.booking.models.dtos.BookingResponseDtoList;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IBookingService {
    BookingResponseDto createBooking(BookingRequestDto dto) throws Exception;
    BookingFullResponseDto getBooking(Long id) throws BadRequestException;
    List<BookingResponseDtoList> getAllBookingByRentalUnit(Long id);
    String deleteBooking(Long id) throws BadRequestException;
}
