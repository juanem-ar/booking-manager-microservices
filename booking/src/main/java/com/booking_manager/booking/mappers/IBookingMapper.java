package com.booking_manager.booking.mappers;

import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDto;
import com.booking_manager.booking.models.dtos.BookingResponseDtoList;
import com.booking_manager.booking.models.entities.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IBookingMapper {
    List<BookingResponseDtoList> bookingListToBookingResponseDtoList(List<BookingEntity> bookingList);
    BookingEntity toEntity(BookingRequestDto dto);
    BookingResponseDto toBookingResponseDto(BookingEntity entity);
    BookingResponseDtoList toBookingResponseDtoList(BookingEntity entity);
}
