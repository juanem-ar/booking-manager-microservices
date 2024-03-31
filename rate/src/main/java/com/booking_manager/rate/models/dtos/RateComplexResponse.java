package com.booking_manager.rate.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateComplexResponse {
    private Double totalAmount;
    private BaseResponse baseResponse;
}
