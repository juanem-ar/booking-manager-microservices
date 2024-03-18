package com.booking_manager.booking.models.dtos;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PaymentRequestDto(Long bookingId, LocalDate checkIn, LocalDate checkOut, Long daysDuration, Double costPerNight, Double partialPayment, int percent, Double debit, Double totalAmount, String code) {
}
