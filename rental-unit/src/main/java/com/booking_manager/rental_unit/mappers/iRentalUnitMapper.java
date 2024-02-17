package com.booking_manager.rental_unit.mappers;

import com.booking_manager.rental_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.rental_unit.models.dtos.RentalUnitResponseDto;
import com.booking_manager.rental_unit.models.entities.RentalUnitEntity;
import com.booking_manager.rental_unit.models.enums.EPool;
import org.apache.coyote.BadRequestException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface iRentalUnitMapper {

    List<RentalUnitResponseDto> toRentalUnitResponseDtoList(List<RentalUnitEntity> entityList);
    @Mapping( source = "businessUnit",target = "businessUnit")
    RentalUnitEntity toRentalUnit(RentalUnitRequestDto dto);
    @Mapping( source = "businessUnit",target = "businessUnit")
    RentalUnitResponseDto toRentalUnitResponseDto(RentalUnitEntity entity);

    default EPool stringToEPool(String dto) throws BadRequestException {
        if (dto.equalsIgnoreCase("PRIVATE") || dto.equalsIgnoreCase("privada"))
            return EPool.POOL_PRIVATE;
        else if(dto.equalsIgnoreCase("PUBLIC") || dto.equalsIgnoreCase("SHARED"))
            return EPool.POOL_SHARED;
        else
            throw new BadRequestException("Invalid pool type. Please insert 'private' or 'shared'");
    }
}
