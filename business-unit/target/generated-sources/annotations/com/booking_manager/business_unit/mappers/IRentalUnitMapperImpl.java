package com.booking_manager.business_unit.mappers;

import com.booking_manager.business_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.RentalUnitResponseDto;
import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
import com.booking_manager.business_unit.models.entities.ServicesEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-10T00:05:02-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class IRentalUnitMapperImpl implements IRentalUnitMapper {

    @Override
    public List<RentalUnitResponseDto> toRentalUnitResponseDtoList(List<RentalUnitEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RentalUnitResponseDto> list = new ArrayList<RentalUnitResponseDto>( entityList.size() );
        for ( RentalUnitEntity rentalUnitEntity : entityList ) {
            list.add( toRentalUnitResponseDto( rentalUnitEntity ) );
        }

        return list;
    }

    @Override
    public RentalUnitEntity toRentalUnit(RentalUnitRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        RentalUnitEntity.RentalUnitEntityBuilder rentalUnitEntity = RentalUnitEntity.builder();

        rentalUnitEntity.businessUnit( rentalUnitRequestDtoToBusinessUnitEntity( dto ) );
        rentalUnitEntity.name( dto.getName() );
        rentalUnitEntity.address( dto.getAddress() );
        rentalUnitEntity.phoneNumber( dto.getPhoneNumber() );
        rentalUnitEntity.description( dto.getDescription() );
        rentalUnitEntity.maximumAmountOfGuests( dto.getMaximumAmountOfGuests() );
        rentalUnitEntity.numberOfBedrooms( dto.getNumberOfBedrooms() );
        rentalUnitEntity.numberOfRooms( dto.getNumberOfRooms() );
        try {
            rentalUnitEntity.pool( stringToEPool( dto.getPool() ) );
        }
        catch ( BadRequestException e ) {
            throw new RuntimeException( e );
        }

        return rentalUnitEntity.build();
    }

    @Override
    public RentalUnitResponseDto toRentalUnitResponseDto(RentalUnitEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RentalUnitResponseDto.RentalUnitResponseDtoBuilder rentalUnitResponseDto = RentalUnitResponseDto.builder();

        rentalUnitResponseDto.businessUnit( entityBusinessUnitId( entity ) );
        List<ServicesEntity> servicesList = entityBusinessUnitServicesList( entity );
        rentalUnitResponseDto.servicesList( servicesListToServicesEntityList( servicesList ) );
        rentalUnitResponseDto.id( entity.getId() );
        rentalUnitResponseDto.name( entity.getName() );
        rentalUnitResponseDto.address( entity.getAddress() );
        rentalUnitResponseDto.phoneNumber( entity.getPhoneNumber() );
        rentalUnitResponseDto.creationDate( entity.getCreationDate() );
        rentalUnitResponseDto.updateDate( entity.getUpdateDate() );
        rentalUnitResponseDto.description( entity.getDescription() );
        rentalUnitResponseDto.maximumAmountOfGuests( entity.getMaximumAmountOfGuests() );
        rentalUnitResponseDto.numberOfBedrooms( entity.getNumberOfBedrooms() );
        rentalUnitResponseDto.numberOfRooms( entity.getNumberOfRooms() );
        rentalUnitResponseDto.status( entity.getStatus() );
        rentalUnitResponseDto.pool( entity.getPool() );

        return rentalUnitResponseDto.build();
    }

    @Override
    public RentalUnitEntity updateEntity(RentalUnitRequestDto dto, RentalUnitEntity entity) {
        if ( dto == null ) {
            return entity;
        }

        if ( entity.getBusinessUnit() == null ) {
            entity.setBusinessUnit( BusinessUnitEntity.builder().build() );
        }
        rentalUnitRequestDtoToBusinessUnitEntity1( dto, entity.getBusinessUnit() );
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        entity.setMaximumAmountOfGuests( dto.getMaximumAmountOfGuests() );
        entity.setNumberOfBedrooms( dto.getNumberOfBedrooms() );
        entity.setNumberOfRooms( dto.getNumberOfRooms() );
        try {
            if ( dto.getPool() != null ) {
                entity.setPool( stringToEPool( dto.getPool() ) );
            }
        }
        catch ( BadRequestException e ) {
            throw new RuntimeException( e );
        }

        return entity;
    }

    protected BusinessUnitEntity rentalUnitRequestDtoToBusinessUnitEntity(RentalUnitRequestDto rentalUnitRequestDto) {
        if ( rentalUnitRequestDto == null ) {
            return null;
        }

        BusinessUnitEntity.BusinessUnitEntityBuilder businessUnitEntity = BusinessUnitEntity.builder();

        businessUnitEntity.id( rentalUnitRequestDto.getBusinessUnit() );

        return businessUnitEntity.build();
    }

    private Long entityBusinessUnitId(RentalUnitEntity rentalUnitEntity) {
        if ( rentalUnitEntity == null ) {
            return null;
        }
        BusinessUnitEntity businessUnit = rentalUnitEntity.getBusinessUnit();
        if ( businessUnit == null ) {
            return null;
        }
        Long id = businessUnit.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private List<ServicesEntity> entityBusinessUnitServicesList(RentalUnitEntity rentalUnitEntity) {
        if ( rentalUnitEntity == null ) {
            return null;
        }
        BusinessUnitEntity businessUnit = rentalUnitEntity.getBusinessUnit();
        if ( businessUnit == null ) {
            return null;
        }
        List<ServicesEntity> servicesList = businessUnit.getServicesList();
        if ( servicesList == null ) {
            return null;
        }
        return servicesList;
    }

    protected void rentalUnitRequestDtoToBusinessUnitEntity1(RentalUnitRequestDto rentalUnitRequestDto, BusinessUnitEntity mappingTarget) {
        if ( rentalUnitRequestDto == null ) {
            return;
        }

        if ( rentalUnitRequestDto.getBusinessUnit() != null ) {
            mappingTarget.setId( rentalUnitRequestDto.getBusinessUnit() );
        }
    }
}
