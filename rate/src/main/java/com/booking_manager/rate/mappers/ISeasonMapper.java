package com.booking_manager.rate.mappers;

import com.booking_manager.rate.models.dtos.SeasonRequestDto;
import com.booking_manager.rate.models.dtos.SeasonResponseDto;
import com.booking_manager.rate.models.entities.SeasonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ISeasonMapper {
    SeasonEntity toEntity(SeasonRequestDto dto);
    SeasonResponseDto toResponseDto(SeasonEntity entity);
    SeasonEntity updateEntity(SeasonRequestDto dto, @MappingTarget SeasonEntity entity);
    List<SeasonResponseDto> toResponseDtoList(List<SeasonEntity> entityList);
}
