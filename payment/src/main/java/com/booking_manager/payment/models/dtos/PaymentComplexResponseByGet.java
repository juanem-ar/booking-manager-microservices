package com.booking_manager.payment.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentComplexResponseByGet {
    private List<PaymentResponseDto> paymentList;
    private BaseResponse baseResponse;
}
