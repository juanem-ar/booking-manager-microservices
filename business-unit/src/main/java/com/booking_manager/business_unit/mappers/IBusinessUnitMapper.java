package com.booking_manager.business_unit.mappers;

import com.booking_manager.business_unit.models.dtos.BusinessUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BusinessUnitResponseDto;
import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
import com.booking_manager.business_unit.models.entities.ServicesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IBusinessUnitMapper {
    BusinessUnitEntity toEntity(BusinessUnitRequestDto dto);
    BusinessUnitResponseDto toResponseDto(BusinessUnitEntity entity);
    BusinessUnitEntity updateEntity(BusinessUnitRequestDto dto, @MappingTarget BusinessUnitEntity entity);

    default List<RentalUnitEntity> entityToRentalUnit(List<RentalUnitEntity> entityList){
        List<RentalUnitEntity> rentalUnitEntityList = new ArrayList<>();
        for (var entity: entityList) {
            if (!entity.getDeleted()){
                rentalUnitEntityList.add(entity);
            }
        }
        return rentalUnitEntityList;
    }
    default List<ServicesEntity> serviceEntityListToServiceEntityList(List<ServicesEntity> entityList){
        List<ServicesEntity> serviceEntityList = new ArrayList<>();
        for (var entity: entityList) {
            if (!entity.getDeleted()){
                serviceEntityList.add(entity);
            }
        }
        return serviceEntityList;
    }
}
