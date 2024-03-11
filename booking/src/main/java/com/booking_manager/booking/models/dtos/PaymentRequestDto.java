package com.booking_manager.booking.models.dtos;

import lombok.Builder;

@Builder
public record PaymentRequestDto(Long bookingId, Long daysDuration, Double costPerNight, Double partialPayment, int percent, Double debit, Double totalAmount) {
}
