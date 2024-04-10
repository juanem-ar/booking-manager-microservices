package com.booking_manager.booking.mappers;

import com.booking_manager.booking.models.dtos.GuestRequestDto;
import com.booking_manager.booking.models.dtos.GuestResponseDto;
import com.booking_manager.booking.models.entities.GuestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IGuestMapper {
    GuestEntity toEntity(GuestRequestDto dto);
    GuestResponseDto toResponseDto(GuestEntity entity);
}
