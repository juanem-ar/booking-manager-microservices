package com.booking_manager.rate.models.dtos;

import com.booking_manager.rate.models.entities.RateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateComplexResponse {
    private RateEntity rate;
    private BaseResponse baseResponse;
}