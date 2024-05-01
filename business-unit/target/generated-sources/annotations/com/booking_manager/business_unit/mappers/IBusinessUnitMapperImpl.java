package com.booking_manager.business_unit.mappers;

import com.booking_manager.business_unit.models.dtos.BusinessUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BusinessUnitResponseDto;
import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T15:01:48-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class IBusinessUnitMapperImpl implements IBusinessUnitMapper {

    @Override
    public BusinessUnitEntity toEntity(BusinessUnitRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        BusinessUnitEntity.BusinessUnitEntityBuilder businessUnitEntity = BusinessUnitEntity.builder();

        businessUnitEntity.name( dto.getName() );
        businessUnitEntity.address( dto.getAddress() );
        businessUnitEntity.phoneNumber( dto.getPhoneNumber() );

        return businessUnitEntity.build();
    }

    @Override
    public BusinessUnitResponseDto toResponseDto(BusinessUnitEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BusinessUnitResponseDto.BusinessUnitResponseDtoBuilder businessUnitResponseDto = BusinessUnitResponseDto.builder();

        businessUnitResponseDto.id( entity.getId() );
        businessUnitResponseDto.name( entity.getName() );
        businessUnitResponseDto.address( entity.getAddress() );
        businessUnitResponseDto.phoneNumber( entity.getPhoneNumber() );
        businessUnitResponseDto.creationDate( entity.getCreationDate() );
        businessUnitResponseDto.updateDate( entity.getUpdateDate() );
        businessUnitResponseDto.rentalUnitList( entityToRentalUnit( entity.getRentalUnitList() ) );
        businessUnitResponseDto.servicesList( serviceEntityListToServiceEntityList( entity.getServicesList() ) );

        return businessUnitResponseDto.build();
    }

    @Override
    public BusinessUnitEntity updateEntity(BusinessUnitRequestDto dto, BusinessUnitEntity entity) {
        if ( dto == null ) {
            return entity;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }

        return entity;
    }
}
