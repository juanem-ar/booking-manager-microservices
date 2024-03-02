package com.booking_manager.booking.services.impl;

import com.booking_manager.booking.mappers.IBookingMapper;
import com.booking_manager.booking.models.dtos.AvailabilityRentalUnitRequestDto;
import com.booking_manager.booking.models.dtos.BaseResponse;
import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDto;
import com.booking_manager.booking.models.entities.BookingEntity;
import com.booking_manager.booking.models.entities.DeletedEntity;
import com.booking_manager.booking.models.enums.EStatus;
import com.booking_manager.booking.repositories.IBookingRepository;
import com.booking_manager.booking.repositories.IDeletedRepository;
import com.booking_manager.booking.services.IBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements IBookingService {
    private final IBookingRepository iBookingRepository;
    private final IBookingMapper iBookingMapper;
    private final WebClient.Builder webClientBuilder;
    private final IDeletedRepository iDeletedRepository;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto dto) throws RuntimeException{

        BaseResponse rentalUnitStatusErrorList = getRentalUnitStatus(dto);

        if (rentalUnitStatusErrorList != null && !rentalUnitStatusErrorList.hastErrors()){
            //TODO agregar validación de fechas cuando se implemente ese servicio
            var entity = iBookingMapper.toEntity(dto);
            bookingSettings(entity);
            var entitySaved = iBookingRepository.save(entity);
            var response = iBookingMapper.toBookingResponseDto(entitySaved);
            //TODO implementar envios de emails con kafka
            log.info("Booking created: {}", entitySaved);

            return response;
        }else{
            log.info("Error when trying to validate rental unit status: {}", rentalUnitStatusErrorList.errorMessage());
            throw new IllegalArgumentException("Service communication error: " + Arrays.toString(rentalUnitStatusErrorList.errorMessage()));
        }
    }

    private BaseResponse getRentalUnitStatus(BookingRequestDto dto) {
        return this.webClientBuilder.build()
                .post()
                .uri("lb://business-service/api/rental-units/available")
                .bodyValue(AvailabilityRentalUnitRequestDto.builder().id(dto.getUnit()).build())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
    }

    @Override
    public BookingResponseDto getBooking(Long id) throws BadRequestException {
        var entity = getBookingEntity(id);
        return iBookingMapper.toBookingResponseDto(entity);
    }

    @Override
    public List<BookingResponseDto> getAllBooking() {
        var bookingList = iBookingRepository.findAllByDeleted(false);
        return iBookingMapper.bookingListToBookingResponseDtoList(bookingList);
    }

    @Override
    public String deleteBooking(Long id) throws BadRequestException {
        var entity = getBookingEntity(id);
        entity.setDeleted(Boolean.TRUE);
        if (entity.getStatus().equals(EStatus.STATUS_IN_PROCESS))
            entity.setStatus(EStatus.STATUS_CANCELLED);
        var entitySaved = iBookingRepository.save(entity);
        var entityDeleted = DeletedEntity.builder()
                .idBooking(entitySaved.getId())
                .build();
        var entityRegistered = iDeletedRepository.save(entityDeleted);
        log.info("Booking Deleted: {}", entitySaved);
        log.info("New Entity Deleted (Saved): {}", entityRegistered);
        return "Booking deleted.";
    }

    public BookingEntity getBookingEntity(Long id) throws BadRequestException {
        //if (iBookingRepository.existsByIdAndDeleted(id, false))
        if (iBookingRepository.existsById(id))
            return iBookingRepository.getReferenceById(id);
        else
            throw new BadRequestException("Invalid Booking id");
    }
    public boolean existsBookingEntity(Long id){
        return iBookingRepository.existsById(id);
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
