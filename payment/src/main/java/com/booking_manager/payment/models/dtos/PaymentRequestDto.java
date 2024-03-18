package com.booking_manager.payment.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    @NotNull(message = "Booking Id is required")
    private Long bookingId;

    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;

    @NotNull(message = "Days duration is required")
    private Long daysDuration;

    @NotNull(message = "Cost per night is required")
    @Min(0)
    private Double costPerNight;

    @NotNull(message = "Partial payment is required")
    @Min(0)
    private Double partialPayment;

    @NotNull(message = "Percent is required")
    @Min(0)
    private int percent;

    @NotNull(message = "Debit amount is required")
    private Double debit;

    @NotNull(message = "Total amount is required")
    @Min(value = 0,message = "Invalid amount")
    private Double totalAmount;

    @Size(min = 3, max = 10)
    private String code;
}
