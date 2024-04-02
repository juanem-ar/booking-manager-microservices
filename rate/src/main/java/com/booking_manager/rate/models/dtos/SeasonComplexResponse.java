package com.booking_manager.rate.models.dtos;

import com.booking_manager.rate.models.entities.SeasonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonComplexResponse {
    private SeasonEntity season;
    private BaseResponse baseResponse;
}