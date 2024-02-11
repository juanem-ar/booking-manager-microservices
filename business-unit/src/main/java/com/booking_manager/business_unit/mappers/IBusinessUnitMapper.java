package com.booking_manager.business_unit.mappers;

import com.booking_manager.business_unit.models.dtos.BusinessUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BusinessUnitResponseDto;
import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IBusinessUnitMapper {
    BusinessUnitEntity toEntity(BusinessUnitRequestDto dto);
    BusinessUnitResponseDto toResponseDto(BusinessUnitEntity entity);
    BusinessUnitEntity updateEntity(BusinessUnitRequestDto dto, @MappingTarget BusinessUnitEntity entity);
}
