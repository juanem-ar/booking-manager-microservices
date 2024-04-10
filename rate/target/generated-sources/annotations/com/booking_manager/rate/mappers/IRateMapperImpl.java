package com.booking_manager.rate.mappers;

import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;
import com.booking_manager.rate.models.dtos.SeasonResponseDto;
import com.booking_manager.rate.models.entities.RateEntity;
import com.booking_manager.rate.models.entities.SeasonEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-10T00:08:36-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class IRateMapperImpl implements IRateMapper {

    @Override
    public RateEntity toEntity(RateRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        RateEntity.RateEntityBuilder rateEntity = RateEntity.builder();

        rateEntity.rate( dto.getRate() );
        rateEntity.businessUnit( dto.getBusinessUnit() );
        rateEntity.rentalUnit( dto.getRentalUnit() );
        rateEntity.amountOfPeople( dto.getAmountOfPeople() );

        return rateEntity.build();
    }

    @Override
    public RateResponseDto toResponseDto(RateEntity savedEntity) {
        if ( savedEntity == null ) {
            return null;
        }

        RateResponseDto.RateResponseDtoBuilder rateResponseDto = RateResponseDto.builder();

        rateResponseDto.id( savedEntity.getId() );
        rateResponseDto.deleted( savedEntity.getDeleted() );
        rateResponseDto.creationDate( savedEntity.getCreationDate() );
        rateResponseDto.updateDate( savedEntity.getUpdateDate() );
        rateResponseDto.rate( savedEntity.getRate() );
        rateResponseDto.businessUnit( savedEntity.getBusinessUnit() );
        rateResponseDto.rentalUnit( savedEntity.getRentalUnit() );
        rateResponseDto.amountOfPeople( savedEntity.getAmountOfPeople() );
        rateResponseDto.season( seasonEntityToSeasonResponseDto( savedEntity.getSeason() ) );

        return rateResponseDto.build();
    }

    @Override
    public RateEntity updateEntity(RateRequestDto dto, RateEntity entity) {
        if ( dto == null ) {
            return entity;
        }

        if ( entity.getSeason() == null ) {
            entity.setSeason( SeasonEntity.builder().build() );
        }
        rateRequestDtoToSeasonEntity( dto, entity.getSeason() );
        if ( dto.getRate() != null ) {
            entity.setRate( dto.getRate() );
        }
        if ( dto.getBusinessUnit() != null ) {
            entity.setBusinessUnit( dto.getBusinessUnit() );
        }
        if ( dto.getRentalUnit() != null ) {
            entity.setRentalUnit( dto.getRentalUnit() );
        }
        entity.setAmountOfPeople( dto.getAmountOfPeople() );

        return entity;
    }

    protected SeasonResponseDto seasonEntityToSeasonResponseDto(SeasonEntity seasonEntity) {
        if ( seasonEntity == null ) {
            return null;
        }

        SeasonResponseDto.SeasonResponseDtoBuilder seasonResponseDto = SeasonResponseDto.builder();

        seasonResponseDto.id( seasonEntity.getId() );
        seasonResponseDto.deleted( seasonEntity.getDeleted() );
        seasonResponseDto.creationDate( seasonEntity.getCreationDate() );
        seasonResponseDto.updateDate( seasonEntity.getUpdateDate() );
        seasonResponseDto.title( seasonEntity.getTitle() );
        seasonResponseDto.seasonType( seasonEntity.getSeasonType() );
        seasonResponseDto.description( seasonEntity.getDescription() );
        seasonResponseDto.businessUnit( seasonEntity.getBusinessUnit() );
        seasonResponseDto.startDate( seasonEntity.getStartDate() );
        seasonResponseDto.endDate( seasonEntity.getEndDate() );

        return seasonResponseDto.build();
    }

    protected void rateRequestDtoToSeasonEntity(RateRequestDto rateRequestDto, SeasonEntity mappingTarget) {
        if ( rateRequestDto == null ) {
            return;
        }

        if ( rateRequestDto.getSeasonId() != null ) {
            mappingTarget.setId( rateRequestDto.getSeasonId() );
        }
    }
}
