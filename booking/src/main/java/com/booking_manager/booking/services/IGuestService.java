package com.booking_manager.booking.services;

import com.booking_manager.booking.models.dtos.GuestRequestDto;
import com.booking_manager.booking.models.dtos.GuestResponseDto;
import com.booking_manager.booking.models.entities.BookingEntity;
import com.booking_manager.booking.models.entities.GuestEntity;

public interface IGuestService {
    GuestResponseDto saveGuest(GuestRequestDto dto);
    GuestEntity addGuest(GuestRequestDto dto);
    GuestResponseDto getGuestByEmail(String email);
    GuestResponseDto addToBookingList(Long guestId, BookingEntity booking);
}
