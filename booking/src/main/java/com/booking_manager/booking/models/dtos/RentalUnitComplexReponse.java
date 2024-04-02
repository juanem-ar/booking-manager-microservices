package com.booking_manager.booking.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalUnitComplexReponse {
    private RentalUnitResponseDto rentalUnit;
    private BaseResponse baseResponse;
}
