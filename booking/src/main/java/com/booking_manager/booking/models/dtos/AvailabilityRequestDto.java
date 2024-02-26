package com.booking_manager.booking.models.dtos;

import java.time.LocalDate;

public record AvailabilityRequestDto(int rentalUnitId, LocalDate checkIn, LocalDate checkOut) {
}
