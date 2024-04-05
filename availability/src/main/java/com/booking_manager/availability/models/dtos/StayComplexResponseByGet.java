package com.booking_manager.availability.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StayComplexResponseByGet {
    private SimpleStayResponseDto stay;
    private BaseResponse baseResponse;
}
