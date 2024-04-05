package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long bookingId;
    private Boolean deleted;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private Double costPerNight;
    private Double partialPayment;
    private int percent;
    private Double debit;
    private Double finalTotalAmount;
    private Double totalAmount;
    private EStatus status;
}
