package com.booking_manager.booking.models.dtos;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StayRequestDto (Long rentalUnitId, Long bookingId, LocalDate checkIn, LocalDate checkOut){}
