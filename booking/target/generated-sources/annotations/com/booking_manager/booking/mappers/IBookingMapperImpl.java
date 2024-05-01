package com.booking_manager.booking.mappers;

import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDto;
import com.booking_manager.booking.models.dtos.BookingResponseDtoList;
import com.booking_manager.booking.models.dtos.GuestRequestDto;
import com.booking_manager.booking.models.dtos.GuestResponseDto;
import com.booking_manager.booking.models.entities.BookingEntity;
import com.booking_manager.booking.models.entities.GuestEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T15:18:58-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class IBookingMapperImpl implements IBookingMapper {

    private final DatatypeFactory datatypeFactory;

    public IBookingMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public List<BookingResponseDtoList> bookingListToBookingResponseDtoList(List<BookingEntity> bookingList) {
        if ( bookingList == null ) {
            return null;
        }

        List<BookingResponseDtoList> list = new ArrayList<BookingResponseDtoList>( bookingList.size() );
        for ( BookingEntity bookingEntity : bookingList ) {
            list.add( toBookingResponseDtoList( bookingEntity ) );
        }

        return list;
    }

    @Override
    public BookingEntity toEntity(BookingRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        BookingEntity.BookingEntityBuilder bookingEntity = BookingEntity.builder();

        bookingEntity.unit( dto.getUnit() );
        bookingEntity.businessUnit( dto.getBusinessUnit() );
        bookingEntity.amountOfPeople( dto.getAmountOfPeople() );
        bookingEntity.guest( guestRequestDtoToGuestEntity( dto.getGuest() ) );

        return bookingEntity.build();
    }

    @Override
    public BookingResponseDto toBookingResponseDto(BookingEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BookingResponseDto bookingResponseDto = new BookingResponseDto();

        bookingResponseDto.setId( entity.getId() );
        bookingResponseDto.setCreationDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( entity.getCreationDate() ) ) );
        bookingResponseDto.setUnit( entity.getUnit() );
        bookingResponseDto.setBusinessUnit( entity.getBusinessUnit() );
        bookingResponseDto.setAmountOfPeople( entity.getAmountOfPeople() );
        bookingResponseDto.setRealCheckIn( entity.getRealCheckIn() );
        bookingResponseDto.setRealCheckOut( entity.getRealCheckOut() );
        bookingResponseDto.setStatus( entity.getStatus() );
        List<Long> list = entity.getServiceIdList();
        if ( list != null ) {
            bookingResponseDto.setServiceIdList( new ArrayList<Long>( list ) );
        }
        bookingResponseDto.setGuest( guestEntityToGuestResponseDto( entity.getGuest() ) );

        return bookingResponseDto;
    }

    @Override
    public BookingResponseDtoList toBookingResponseDtoList(BookingEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BookingResponseDtoList bookingResponseDtoList = new BookingResponseDtoList();

        bookingResponseDtoList.setId( entity.getId() );
        bookingResponseDtoList.setCreationDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( entity.getCreationDate() ) ) );
        bookingResponseDtoList.setUnit( entity.getUnit() );
        bookingResponseDtoList.setBusinessUnit( entity.getBusinessUnit() );
        bookingResponseDtoList.setAmountOfPeople( entity.getAmountOfPeople() );
        bookingResponseDtoList.setRealCheckIn( entity.getRealCheckIn() );
        bookingResponseDtoList.setRealCheckOut( entity.getRealCheckOut() );
        List<Long> list = entity.getServiceIdList();
        if ( list != null ) {
            bookingResponseDtoList.setServiceIdList( new ArrayList<Long>( list ) );
        }
        bookingResponseDtoList.setStatus( entity.getStatus() );
        bookingResponseDtoList.setGuest( guestEntityToGuestResponseDto( entity.getGuest() ) );

        return bookingResponseDtoList;
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    protected GuestEntity guestRequestDtoToGuestEntity(GuestRequestDto guestRequestDto) {
        if ( guestRequestDto == null ) {
            return null;
        }

        GuestEntity.GuestEntityBuilder guestEntity = GuestEntity.builder();

        guestEntity.name( guestRequestDto.getName() );
        guestEntity.lastName( guestRequestDto.getLastName() );
        guestEntity.documentType( guestRequestDto.getDocumentType() );
        guestEntity.documentNumber( guestRequestDto.getDocumentNumber() );
        guestEntity.areaCode( guestRequestDto.getAreaCode() );
        guestEntity.phoneNumber( guestRequestDto.getPhoneNumber() );
        guestEntity.email( guestRequestDto.getEmail() );
        guestEntity.age( guestRequestDto.getAge() );
        guestEntity.dateOfBirth( guestRequestDto.getDateOfBirth() );
        guestEntity.address( guestRequestDto.getAddress() );
        guestEntity.city( guestRequestDto.getCity() );
        guestEntity.country( guestRequestDto.getCountry() );
        guestEntity.origin( guestRequestDto.getOrigin() );
        guestEntity.maritalStatus( guestRequestDto.getMaritalStatus() );

        return guestEntity.build();
    }

    protected GuestResponseDto guestEntityToGuestResponseDto(GuestEntity guestEntity) {
        if ( guestEntity == null ) {
            return null;
        }

        GuestResponseDto.GuestResponseDtoBuilder guestResponseDto = GuestResponseDto.builder();

        guestResponseDto.id( guestEntity.getId() );
        guestResponseDto.creationDate( guestEntity.getCreationDate() );
        guestResponseDto.updateDate( guestEntity.getUpdateDate() );
        guestResponseDto.deleted( guestEntity.isDeleted() );
        guestResponseDto.name( guestEntity.getName() );
        guestResponseDto.lastName( guestEntity.getLastName() );
        guestResponseDto.documentType( guestEntity.getDocumentType() );
        guestResponseDto.documentNumber( guestEntity.getDocumentNumber() );
        guestResponseDto.areaCode( guestEntity.getAreaCode() );
        guestResponseDto.phoneNumber( guestEntity.getPhoneNumber() );
        guestResponseDto.email( guestEntity.getEmail() );
        guestResponseDto.age( guestEntity.getAge() );
        guestResponseDto.dateOfBirth( guestEntity.getDateOfBirth() );
        guestResponseDto.address( guestEntity.getAddress() );
        guestResponseDto.city( guestEntity.getCity() );
        guestResponseDto.country( guestEntity.getCountry() );
        guestResponseDto.origin( guestEntity.getOrigin() );
        guestResponseDto.maritalStatus( guestEntity.getMaritalStatus() );

        return guestResponseDto.build();
    }
}
