package com.booking_manager.payment.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPaymentRequestDto {

        @NotNull(message = "Booking Id is required")
        private Long bookingId;

        @NotNull(message = "Payment is required")
        @Min(0)
        private Double payment;

        @Size(min = 3, max = 10)
        private String code;
}
