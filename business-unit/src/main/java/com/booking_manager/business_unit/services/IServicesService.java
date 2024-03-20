package com.booking_manager.business_unit.services;

import com.booking_manager.business_unit.models.dtos.ServiceRequestDto;
import com.booking_manager.business_unit.models.dtos.ServiceResponseDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IServicesService {
    ServiceResponseDto saveService(ServiceRequestDto dto) throws BadRequestException;
    ServiceResponseDto getService(Long id) throws BadRequestException;
    List<ServiceResponseDto> getServicesByBusinessId(Long businessUnitId);
    ServiceResponseDto editService(Long id, ServiceRequestDto dto) throws BadRequestException;
    String deleteService(Long id) throws BadRequestException;
}
