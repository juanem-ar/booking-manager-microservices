package com.booking_manager.payment.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    @NotNull(message = "Booking Id is required")
    private Long bookingId;

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
    @Max(value = 100, message = "percent max value is 100")
    private int percent;

    @NotNull(message = "Debit amount is required")
    @Min(value = 0,message = "Invalid amount")
    private Double debit;

    @NotNull(message = "Total amount is required")
    @Min(value = 0,message = "Invalid amount")
    private Double totalAmount;
}
