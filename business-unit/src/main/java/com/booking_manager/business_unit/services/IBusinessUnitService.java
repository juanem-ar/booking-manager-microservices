package com.booking_manager.business_unit.services;

import com.booking_manager.business_unit.models.dtos.BusinessUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BusinessUnitResponseDto;

public interface IBusinessUnitService {
    BusinessUnitResponseDto saveBusinessUnit(BusinessUnitRequestDto dto);
    BusinessUnitResponseDto updateBusinessUnit(BusinessUnitRequestDto dto, Long id);
    String deleteBusinessUnit(Long id);
    BusinessUnitResponseDto getBusinessUnitResponseDtoById(Long id);
    boolean existsBusinessUnitById(Long id);
}
