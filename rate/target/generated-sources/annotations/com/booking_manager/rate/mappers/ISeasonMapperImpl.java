package com.booking_manager.rate.mappers;

import com.booking_manager.rate.models.dtos.SeasonRequestDto;
import com.booking_manager.rate.models.dtos.SeasonResponseDto;
import com.booking_manager.rate.models.entities.SeasonEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T15:20:11-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ISeasonMapperImpl implements ISeasonMapper {

    @Override
    public SeasonEntity toEntity(SeasonRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        SeasonEntity.SeasonEntityBuilder seasonEntity = SeasonEntity.builder();

        seasonEntity.title( dto.getTitle() );
        seasonEntity.seasonType( dto.getSeasonType() );
        seasonEntity.description( dto.getDescription() );
        seasonEntity.businessUnit( dto.getBusinessUnit() );
        seasonEntity.startDate( dto.getStartDate() );
        seasonEntity.endDate( dto.getEndDate() );

        return seasonEntity.build();
    }

    @Override
    public SeasonResponseDto toResponseDto(SeasonEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SeasonResponseDto.SeasonResponseDtoBuilder seasonResponseDto = SeasonResponseDto.builder();

        seasonResponseDto.id( entity.getId() );
        seasonResponseDto.deleted( entity.getDeleted() );
        seasonResponseDto.creationDate( entity.getCreationDate() );
        seasonResponseDto.updateDate( entity.getUpdateDate() );
        seasonResponseDto.title( entity.getTitle() );
        seasonResponseDto.seasonType( entity.getSeasonType() );
        seasonResponseDto.description( entity.getDescription() );
        seasonResponseDto.businessUnit( entity.getBusinessUnit() );
        seasonResponseDto.startDate( entity.getStartDate() );
        seasonResponseDto.endDate( entity.getEndDate() );

        return seasonResponseDto.build();
    }

    @Override
    public SeasonEntity updateEntity(SeasonRequestDto dto, SeasonEntity entity) {
        if ( dto == null ) {
            return entity;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getSeasonType() != null ) {
            entity.setSeasonType( dto.getSeasonType() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getBusinessUnit() != null ) {
            entity.setBusinessUnit( dto.getBusinessUnit() );
        }
        if ( dto.getStartDate() != null ) {
            entity.setStartDate( dto.getStartDate() );
        }
        if ( dto.getEndDate() != null ) {
            entity.setEndDate( dto.getEndDate() );
        }

        return entity;
    }

    @Override
    public List<SeasonResponseDto> toResponseDtoList(List<SeasonEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SeasonResponseDto> list = new ArrayList<SeasonResponseDto>( entityList.size() );
        for ( SeasonEntity seasonEntity : entityList ) {
            list.add( toResponseDto( seasonEntity ) );
        }

        return list;
    }
}
