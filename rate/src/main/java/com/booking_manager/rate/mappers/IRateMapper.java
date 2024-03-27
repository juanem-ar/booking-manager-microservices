package com.booking_manager.rate.mappers;

import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;
import com.booking_manager.rate.models.entities.RateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IRateMapper {
    RateEntity toEntity(RateRequestDto dto);
    RateResponseDto toResponseDto(RateEntity savedEntity);
}
