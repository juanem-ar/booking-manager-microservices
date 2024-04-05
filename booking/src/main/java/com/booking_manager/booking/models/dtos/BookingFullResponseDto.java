package com.booking_manager.booking.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingFullResponseDto {
    private BookingResponseDtoList booking;
    private SimpleStayResponseDto stay;
    private List<PaymentResponseDto> paymentList;
}
