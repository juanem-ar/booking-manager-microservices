package com.booking_manager.payment.mappers;

import com.booking_manager.payment.models.dtos.CouponRequestDto;
import com.booking_manager.payment.models.dtos.CouponResponseDto;
import com.booking_manager.payment.models.entities.CouponEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-10T00:42:44-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ICouponMapperImpl implements ICouponMapper {

    @Override
    public CouponEntity toEntity(CouponRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        CouponEntity.CouponEntityBuilder couponEntity = CouponEntity.builder();

        couponEntity.businessUnitId( dto.getBusinessUnitId() );
        couponEntity.expirationDate( dto.getExpirationDate() );
        couponEntity.code( dto.getCode() );
        couponEntity.type( dto.getType() );
        couponEntity.amount( dto.getAmount() );
        couponEntity.checkInDateAfter( dto.getCheckInDateAfter() );
        couponEntity.checkOutDateBefore( dto.getCheckOutDateBefore() );
        couponEntity.minimumDays( dto.getMinimumDays() );
        couponEntity.usageLimit( dto.getUsageLimit() );

        return couponEntity.build();
    }

    @Override
    public CouponResponseDto toDto(CouponEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CouponResponseDto.CouponResponseDtoBuilder couponResponseDto = CouponResponseDto.builder();

        couponResponseDto.id( entity.getId() );
        couponResponseDto.businessUnitId( entity.getBusinessUnitId() );
        couponResponseDto.deleted( entity.getDeleted() );
        couponResponseDto.creationDate( entity.getCreationDate() );
        couponResponseDto.updateDate( entity.getUpdateDate() );
        couponResponseDto.expirationDate( entity.getExpirationDate() );
        couponResponseDto.code( entity.getCode() );
        couponResponseDto.type( entity.getType() );
        couponResponseDto.amount( entity.getAmount() );
        couponResponseDto.checkInDateAfter( entity.getCheckInDateAfter() );
        couponResponseDto.checkOutDateBefore( entity.getCheckOutDateBefore() );
        couponResponseDto.minimumDays( entity.getMinimumDays() );
        couponResponseDto.usageLimit( entity.getUsageLimit() );
        couponResponseDto.usageCount( entity.getUsageCount() );

        return couponResponseDto.build();
    }

    @Override
    public List<CouponResponseDto> toDtoList(List<CouponEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CouponResponseDto> list = new ArrayList<CouponResponseDto>( entityList.size() );
        for ( CouponEntity couponEntity : entityList ) {
            list.add( toDto( couponEntity ) );
        }

        return list;
    }

    @Override
    public CouponEntity updateToEntity(CouponRequestDto dto, CouponEntity entity) {
        if ( dto == null ) {
            return entity;
        }

        if ( dto.getBusinessUnitId() != null ) {
            entity.setBusinessUnitId( dto.getBusinessUnitId() );
        }
        if ( dto.getExpirationDate() != null ) {
            entity.setExpirationDate( dto.getExpirationDate() );
        }
        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
        if ( dto.getAmount() != null ) {
            entity.setAmount( dto.getAmount() );
        }
        if ( dto.getCheckInDateAfter() != null ) {
            entity.setCheckInDateAfter( dto.getCheckInDateAfter() );
        }
        if ( dto.getCheckOutDateBefore() != null ) {
            entity.setCheckOutDateBefore( dto.getCheckOutDateBefore() );
        }
        entity.setMinimumDays( dto.getMinimumDays() );
        entity.setUsageLimit( dto.getUsageLimit() );

        return entity;
    }
}
