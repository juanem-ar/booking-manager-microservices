package com.booking_manager.rate.mappers;

import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;
import com.booking_manager.rate.models.entities.RateEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IRateMapper {
    RateEntity toEntity(RateRequestDto dto);
    RateResponseDto toResponseDto(RateEntity savedEntity);
    @Mapping( source = "seasonId",target = "season.id")
    RateEntity updateEntity(RateRequestDto dto, @MappingTarget RateEntity entity);
}
