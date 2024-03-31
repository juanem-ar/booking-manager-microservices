package com.booking_manager.business_unit.mappers;

import com.booking_manager.business_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.RentalUnitResponseDto;
import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
import com.booking_manager.business_unit.models.entities.ServicesEntity;
import com.booking_manager.business_unit.models.enums.EPool;
import org.apache.coyote.BadRequestException;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IRentalUnitMapper {
    @Mapping(source = "businessUnit.servicesEntityList", target = "serviceList")
    List<RentalUnitResponseDto> toRentalUnitResponseDtoList(List<RentalUnitEntity> entityList);
    @Mapping( source = "businessUnit",target = "businessUnit.id")
    RentalUnitEntity toRentalUnit(RentalUnitRequestDto dto);
    @Mapping( source = "businessUnit.id",target = "businessUnit")
    @Mapping(source = "businessUnit.servicesList", target = "servicesList")
    RentalUnitResponseDto toRentalUnitResponseDto(RentalUnitEntity entity);
    @Mapping( source = "businessUnit",target = "businessUnit.id")
    RentalUnitEntity updateEntity(RentalUnitRequestDto dto, @MappingTarget RentalUnitEntity entity);

    default EPool stringToEPool(String dto) throws BadRequestException {
        if (dto.equalsIgnoreCase("PRIVATE") || dto.equalsIgnoreCase("privada"))
            return EPool.POOL_PRIVATE;
        else if(dto.equalsIgnoreCase("PUBLIC") || dto.equalsIgnoreCase("SHARED"))
            return EPool.POOL_SHARED;
        else
            throw new BadRequestException("Invalid pool type. Please insert 'private' or 'shared'");
    }

    default List<ServicesEntity> servicesListToServicesEntityList(List<ServicesEntity> servicesList){
        List<ServicesEntity> serviceEntityList = new ArrayList<>();
        for (var entity: servicesList) {
            if (!entity.getDeleted()){
                serviceEntityList.add(entity);
            }
        }
        return serviceEntityList;
    }
}
