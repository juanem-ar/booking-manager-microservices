package com.booking_manager.booking.mappers;

import com.booking_manager.booking.models.dtos.GuestRequestDto;
import com.booking_manager.booking.models.dtos.GuestResponseDto;
import com.booking_manager.booking.models.entities.GuestEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-10T00:07:23-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class IGuestMapperImpl implements IGuestMapper {

    @Override
    public GuestEntity toEntity(GuestRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        GuestEntity.GuestEntityBuilder guestEntity = GuestEntity.builder();

        guestEntity.name( dto.getName() );
        guestEntity.lastName( dto.getLastName() );
        guestEntity.documentType( dto.getDocumentType() );
        guestEntity.documentNumber( dto.getDocumentNumber() );
        guestEntity.areaCode( dto.getAreaCode() );
        guestEntity.phoneNumber( dto.getPhoneNumber() );
        guestEntity.email( dto.getEmail() );
        guestEntity.age( dto.getAge() );
        guestEntity.dateOfBirth( dto.getDateOfBirth() );
        guestEntity.address( dto.getAddress() );
        guestEntity.city( dto.getCity() );
        guestEntity.country( dto.getCountry() );
        guestEntity.origin( dto.getOrigin() );
        guestEntity.maritalStatus( dto.getMaritalStatus() );

        return guestEntity.build();
    }

    @Override
    public GuestResponseDto toResponseDto(GuestEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GuestResponseDto.GuestResponseDtoBuilder guestResponseDto = GuestResponseDto.builder();

        guestResponseDto.id( entity.getId() );
        guestResponseDto.creationDate( entity.getCreationDate() );
        guestResponseDto.updateDate( entity.getUpdateDate() );
        guestResponseDto.deleted( entity.isDeleted() );
        guestResponseDto.name( entity.getName() );
        guestResponseDto.lastName( entity.getLastName() );
        guestResponseDto.documentType( entity.getDocumentType() );
        guestResponseDto.documentNumber( entity.getDocumentNumber() );
        guestResponseDto.areaCode( entity.getAreaCode() );
        guestResponseDto.phoneNumber( entity.getPhoneNumber() );
        guestResponseDto.email( entity.getEmail() );
        guestResponseDto.age( entity.getAge() );
        guestResponseDto.dateOfBirth( entity.getDateOfBirth() );
        guestResponseDto.address( entity.getAddress() );
        guestResponseDto.city( entity.getCity() );
        guestResponseDto.country( entity.getCountry() );
        guestResponseDto.origin( entity.getOrigin() );
        guestResponseDto.maritalStatus( entity.getMaritalStatus() );

        return guestResponseDto.build();
    }
}
