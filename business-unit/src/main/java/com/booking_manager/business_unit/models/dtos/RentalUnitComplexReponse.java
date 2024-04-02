package com.booking_manager.business_unit.models.dtos;

import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
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
