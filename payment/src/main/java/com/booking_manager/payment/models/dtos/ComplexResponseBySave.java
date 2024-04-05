package com.booking_manager.payment.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexResponseBySave {
    private PaymentResponseDto object;
    private BaseResponse baseResponse;
}
