package com.booking_manager.business_unit.mappers;

import com.booking_manager.business_unit.models.dtos.ServiceRequestDto;
import com.booking_manager.business_unit.models.dtos.ServiceResponseDto;
import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import com.booking_manager.business_unit.models.entities.ServicesEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T15:01:49-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class IServiceMapperImpl implements IServiceMapper {

    @Override
    public ServiceResponseDto toResponseDto(ServicesEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ServiceResponseDto.ServiceResponseDtoBuilder serviceResponseDto = ServiceResponseDto.builder();

        serviceResponseDto.id( entity.getId() );
        serviceResponseDto.deleted( entity.getDeleted() );
        serviceResponseDto.creationDate( entity.getCreationDate() );
        serviceResponseDto.updateDate( entity.getUpdateDate() );
        serviceResponseDto.title( entity.getTitle() );
        serviceResponseDto.description( entity.getDescription() );
        serviceResponseDto.price( entity.getPrice() );

        return serviceResponseDto.build();
    }

    @Override
    public ServicesEntity toEntity(ServiceRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        ServicesEntity.ServicesEntityBuilder servicesEntity = ServicesEntity.builder();

        servicesEntity.businessUnit( serviceRequestDtoToBusinessUnitEntity( dto ) );
        servicesEntity.title( dto.getTitle() );
        servicesEntity.description( dto.getDescription() );
        servicesEntity.price( dto.getPrice() );

        return servicesEntity.build();
    }

    @Override
    public List<ServiceResponseDto> toServicesResponseDtoList(List<ServicesEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ServiceResponseDto> list = new ArrayList<ServiceResponseDto>( entityList.size() );
        for ( ServicesEntity servicesEntity : entityList ) {
            list.add( toResponseDto( servicesEntity ) );
        }

        return list;
    }

    @Override
    public ServicesEntity updateEntity(ServiceRequestDto dto, ServicesEntity entity) {
        if ( dto == null ) {
            return entity;
        }

        if ( entity.getBusinessUnit() == null ) {
            entity.setBusinessUnit( BusinessUnitEntity.builder().build() );
        }
        serviceRequestDtoToBusinessUnitEntity1( dto, entity.getBusinessUnit() );
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }

        return entity;
    }

    protected BusinessUnitEntity serviceRequestDtoToBusinessUnitEntity(ServiceRequestDto serviceRequestDto) {
        if ( serviceRequestDto == null ) {
            return null;
        }

        BusinessUnitEntity.BusinessUnitEntityBuilder businessUnitEntity = BusinessUnitEntity.builder();

        businessUnitEntity.id( serviceRequestDto.getBusinessUnitId() );

        return businessUnitEntity.build();
    }

    protected void serviceRequestDtoToBusinessUnitEntity1(ServiceRequestDto serviceRequestDto, BusinessUnitEntity mappingTarget) {
        if ( serviceRequestDto == null ) {
            return;
        }

        if ( serviceRequestDto.getBusinessUnitId() != null ) {
            mappingTarget.setId( serviceRequestDto.getBusinessUnitId() );
        }
    }
}
