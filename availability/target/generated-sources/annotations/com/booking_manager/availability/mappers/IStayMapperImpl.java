package com.booking_manager.availability.mappers;

import com.booking_manager.availability.models.dtos.SimpleStayResponseDto;
import com.booking_manager.availability.models.dtos.StayRequestDto;
import com.booking_manager.availability.models.dtos.StayResponseDto;
import com.booking_manager.availability.models.entities.StayEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-10T01:01:03-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class IStayMapperImpl implements IStayMapper {

    @Override
    public List<StayResponseDto> toStayResponseDtoList(List<StayEntity> staysList) {
        if ( staysList == null ) {
            return null;
        }

        List<StayResponseDto> list = new ArrayList<StayResponseDto>( staysList.size() );
        for ( StayEntity stayEntity : staysList ) {
            list.add( stayEntityToStayResponseDto( stayEntity ) );
        }

        return list;
    }

    @Override
    public StayEntity toStayEntity(StayRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        StayEntity.StayEntityBuilder stayEntity = StayEntity.builder();

        stayEntity.rentalUnitId( dto.getRentalUnitId() );
        stayEntity.businessUnitId( dto.getBusinessUnitId() );
        stayEntity.bookingId( dto.getBookingId() );
        stayEntity.checkIn( dto.getCheckIn() );
        stayEntity.checkOut( dto.getCheckOut() );

        return stayEntity.build();
    }

    @Override
    public SimpleStayResponseDto toSimpleStayResponseDto(StayEntity stay) {
        if ( stay == null ) {
            return null;
        }

        SimpleStayResponseDto.SimpleStayResponseDtoBuilder simpleStayResponseDto = SimpleStayResponseDto.builder();

        simpleStayResponseDto.id( stay.getId() );
        simpleStayResponseDto.deleted( stay.getDeleted() );
        simpleStayResponseDto.checkIn( stay.getCheckIn() );
        simpleStayResponseDto.checkOut( stay.getCheckOut() );

        return simpleStayResponseDto.build();
    }

    protected StayResponseDto stayEntityToStayResponseDto(StayEntity stayEntity) {
        if ( stayEntity == null ) {
            return null;
        }

        StayResponseDto.StayResponseDtoBuilder stayResponseDto = StayResponseDto.builder();

        stayResponseDto.id( stayEntity.getId() );
        stayResponseDto.deleted( stayEntity.getDeleted() );
        stayResponseDto.rentalUnitId( stayEntity.getRentalUnitId() );
        stayResponseDto.businessUnitId( stayEntity.getBusinessUnitId() );
        stayResponseDto.bookingId( stayEntity.getBookingId() );
        stayResponseDto.checkIn( stayEntity.getCheckIn() );
        stayResponseDto.checkOut( stayEntity.getCheckOut() );

        return stayResponseDto.build();
    }
}
