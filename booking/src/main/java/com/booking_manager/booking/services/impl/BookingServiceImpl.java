package com.booking_manager.booking.services.impl;

import com.booking_manager.booking.mappers.IBookingMapper;
import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDto;
import com.booking_manager.booking.models.entities.BookingEntity;
import com.booking_manager.booking.models.enums.EStatus;
import com.booking_manager.booking.repositories.IBookingRepository;
import com.booking_manager.booking.services.IBookingService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {
    private final IBookingRepository iBookingRepository;
    private final IBookingMapper iBookingMapper;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto dto) {
        //TODO VALIDAR EXISTENCIA DEL RENTALUNIT (dto.unit)
        var entity = iBookingMapper.toEntity(dto);
        bookingSettings(entity);
        var entitySaved = iBookingRepository.save(entity);
        var response = iBookingMapper.toBookingResponseDto(entitySaved);
        //TODO implementar envios de emails con kafka
        return response;
    }

    @Override
    public BookingResponseDto getBooking(Long id) throws BadRequestException {
        var entity = existsBooking(id);
        return iBookingMapper.toBookingResponseDto(entity);
    }

    @Override
    public List<BookingResponseDto> getAllBooking() {
        var bookingList = iBookingRepository.findAllByDeleted(false);
        return iBookingMapper.bookingListToBookingResponseDtoList(bookingList);
    }

    @Override
    public String deleteBooking(Long id) throws BadRequestException {
        var entity = existsBooking(id);
        entity.setDeleted(Boolean.TRUE);
        iBookingRepository.save(entity);
        return "Booking deleted.";
    }

    public BookingEntity existsBooking(Long id) throws BadRequestException {
        if (iBookingRepository.existsByIdAndDeleted(id, false))
            return iBookingRepository.getReferenceById(id);
        else
            throw new BadRequestException("Invalid Booking id");
    }

    public void bookingSettings(BookingEntity entity){
        long daysToBooking = DAYS.between(entity.getCheckIn(), entity.getCheckOut());
        entity.setDeleted(Boolean.FALSE);
        entity.setStatus(EStatus.STATUS_IN_PROCESS);
        entity.setTotalAmount(entity.getCostPerNight() * daysToBooking);
        int percent = entity.getPercent();
        var percentToDecimal = Double.valueOf((double) percent / 100);
        entity.setPartialPayment(entity.getTotalAmount() * percentToDecimal);
        entity.setDebit(entity.getTotalAmount() - entity.getPartialPayment());
    }
}
