package com.booking_manager.payment.mappers;

import com.booking_manager.payment.models.dtos.CouponRequestDto;
import com.booking_manager.payment.models.dtos.CouponResponseDto;
import com.booking_manager.payment.models.entities.CouponEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ICouponMapper {
    CouponEntity toEntity(CouponRequestDto dto);
    CouponResponseDto toDto(CouponEntity entity);
    List<CouponResponseDto> toDtoList(List<CouponEntity> entityList);

    CouponEntity updateToEntity(CouponRequestDto dto, @MappingTarget CouponEntity entity);
}
