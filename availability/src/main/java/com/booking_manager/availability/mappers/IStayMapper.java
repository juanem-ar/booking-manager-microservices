package com.booking_manager.availability.mappers;

import com.booking_manager.availability.models.dtos.SimpleStayResponseDto;
import com.booking_manager.availability.models.dtos.StayRequestDto;
import com.booking_manager.availability.models.dtos.StayResponseDto;
import com.booking_manager.availability.models.entities.StayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IStayMapper {
    List<StayResponseDto> toStayResponseDtoList(List<StayEntity> staysList);
    StayEntity toStayEntity(StayRequestDto dto);
    SimpleStayResponseDto toSimpleStayResponseDto(StayEntity stay);
}
