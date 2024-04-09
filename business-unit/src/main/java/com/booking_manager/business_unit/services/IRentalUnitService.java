package com.booking_manager.business_unit.services;

import com.booking_manager.business_unit.models.dtos.RentalUnitComplexReponse;
import com.booking_manager.business_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.RentalUnitResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRentalUnitService {
    RentalUnitResponseDto saveRentalUnit(RentalUnitRequestDto dto, List<MultipartFile> files) throws Exception;

    RentalUnitResponseDto getRentalUnitResponseDtoById(Long id);

    List<RentalUnitResponseDto> getAllRentalUnitResponseDtoByBusinessUnitId(Long id);

    RentalUnitResponseDto updateRentalUnit(RentalUnitRequestDto dto, Long id);

    String deleteRentalUnit(Long id);

    String changeStatusRentalUnit(Long id);
    RentalUnitComplexReponse existsRentalUnitByAvailableRequestDto(Long id);
}
