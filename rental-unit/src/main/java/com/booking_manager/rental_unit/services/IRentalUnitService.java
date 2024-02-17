package com.booking_manager.rental_unit.services;

import com.booking_manager.rental_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.rental_unit.models.dtos.RentalUnitResponseDto;

import java.util.List;

public interface IRentalUnitService {
    RentalUnitResponseDto saveRentalUnit(RentalUnitRequestDto dto);

    RentalUnitResponseDto getRentalUnitResponseDtoById(Long id);
    List<RentalUnitResponseDto> getAllRentalUnitResponseDtoByBusinessUnitId(Long id);

    RentalUnitResponseDto updateRentalUnit(RentalUnitRequestDto dto, Long id);

    String deleteRentalUnit(Long id);
}
