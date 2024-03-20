package com.booking_manager.business_unit.mappers;

import com.booking_manager.business_unit.models.dtos.ServiceRequestDto;
import com.booking_manager.business_unit.models.dtos.ServiceResponseDto;
import com.booking_manager.business_unit.models.entities.ServicesEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IServiceMapper {
    ServiceResponseDto toResponseDto(ServicesEntity entity);
    @Mapping( source = "businessUnitId",target = "businessUnit.id")
    ServicesEntity toEntity(ServiceRequestDto dto);
    List<ServiceResponseDto> toServicesResponseDtoList(List<ServicesEntity> entityList);
    @Mapping( source = "businessUnitId",target = "businessUnit.id")
    ServicesEntity updateEntity(ServiceRequestDto dto,@MappingTarget ServicesEntity entity);
}
