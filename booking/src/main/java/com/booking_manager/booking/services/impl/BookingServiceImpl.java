package com.booking_manager.booking.services.impl;

import com.booking_manager.booking.mappers.IBookingMapper;
import com.booking_manager.booking.models.dtos.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements IBookingService {
    private final IBookingRepository iBookingRepository;
    private final IBookingMapper iBookingMapper;
    private final WebClient.Builder webClientBuilder;
    private final IDeletedRepository iDeletedRepository;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto dto) throws Exception {
        dto.validatePeriod(dto.getCheckIn(),dto.getCheckOut());
        BaseResponse rentalUnitStatusErrorList = getRentalUnitStatus(dto);
        //TODO obtener los servicios de la unidad de negocio para agregarlos al monto total. En vez de obtener un base response la linea superior, debe recibir un complex response con la entidad de BU

        if (rentalUnitStatusErrorList != null && !rentalUnitStatusErrorList.hastErrors()){
            RateComplexResponse rateServiceResponse = getTotalAmount(dto);
            if (rateServiceResponse != null && !rateServiceResponse.getBaseResponse().hastErrors()){
                //Se establece el monto total del request
                dto.setTotalAmount(rateServiceResponse.getTotalAmount());
            }else{
                throw new IllegalArgumentException("Rate Service communication error: " + Arrays.toString(rateServiceResponse.getBaseResponse().errorMessage()));
            }
            var entity = iBookingMapper.toEntity(dto);
            entity.setDeleted(Boolean.FALSE);
            entity.setStatus(EStatus.STATUS_IN_PROCESS);
            var entitySaved = iBookingRepository.save(entity);

            try{
                BaseResponse savedStay = checkAvailabilityAndSaveStay(dto, entitySaved.getId());

                if (savedStay != null && !savedStay.hastErrors()){

                    PaymentComplexResponse savedPayment = savePayment(dto, entitySaved.getId());

                    if (savedPayment.getBaseResponse() != null && !savedPayment.getBaseResponse().hastErrors()) {
                        var paymentResponse = savedPayment.getObject();

                        //TODO EL ESTADO ACEPTADO DEBE SER ESTABLECIDO CUANDO SE CONFIRMA EL PAGO
                        entitySaved.setStatus(EStatus.STATUS_ACCEPTED);

                        iBookingRepository.save(entity);
                        var response = iBookingMapper.toBookingResponseDto(entitySaved);
                        response.setCheckIn(dto.getCheckIn());
                        response.setCheckOut(dto.getCheckOut());
                        response.setCostPerNight(paymentResponse.getCostPerNight());
                        response.setPartialPayment(paymentResponse.getPartialPayment());
                        response.setPercent(paymentResponse.getPercent());
                        response.setDebit(paymentResponse.getDebit());
                        response.setTotalAmount(paymentResponse.getTotalAmount());
                        response.setPaymentStatus(paymentResponse.getStatus());
                        //TODO implementar envios de emails con kafka
                        log.info("Booking created: {}", entitySaved);
                        return response;
                    }else{
                        log.info("Error when trying to save the payment: {}", savedPayment.getBaseResponse().errorMessage());
                        throw new IllegalArgumentException("Service communication error: " + Arrays.stream(savedPayment.getBaseResponse().errorMessage()).iterator().toString().toString());
                    }
                }else{
                    log.info("Error when trying to save the stay: {}", savedStay.errorMessage());
                    throw new IllegalArgumentException("Service communication error: " + Arrays.toString(savedStay.errorMessage()));
                }
            }catch (Exception e){
                deleteStay(entitySaved.getId());
                //TODO mejorar esta toma del error. Se precisa mostrar los errores q arrastran las listas de errores.
                throw new IllegalArgumentException("Service communication error: " + e.getMessage());
            }
        }else{
            log.info("Error when trying to validate rental unit status: {}", rentalUnitStatusErrorList.errorMessage());
            throw new IllegalArgumentException("Service communication error: " + Arrays.toString(rentalUnitStatusErrorList.errorMessage()));
        }
    }

    private BaseResponse checkAvailabilityAndSaveStay(BookingRequestDto dto, Long id) {
        return this.webClientBuilder.build()
                .post()
                .uri("lb://availability-service/api/availabilities")
                .bodyValue(
                        StayRequestDto.builder()
                                .rentalUnitId(dto.getUnit())
                                .businessUnitId(dto.getBusinessUnit())
                                .bookingId(id)
                                .checkIn(dto.getCheckIn())
                                .checkOut(dto.getCheckOut())
                                .build()
                )
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
    }

    private BaseResponse getRentalUnitStatus(BookingRequestDto dto) {
        return this.webClientBuilder.build()
                .post()
                .uri("lb://business-service/api/rental-units/available")
                .bodyValue(
                        AvailabilityRentalUnitRequestDto.builder()
                                .id(dto.getUnit())
                                .build()
                )
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
    }

    private PaymentComplexResponse savePayment(BookingRequestDto dto, Long bookingId) {
        //TODO debe viajar el valor total calculado en el servicio de rates
        return this.webClientBuilder.build()
                .post()
                .uri("lb://payment-service/api/payments/bookings/"+ bookingId)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(PaymentComplexResponse.class)
                .block();
    }

    private RateComplexResponse getTotalAmount(BookingRequestDto dto) {
        return this.webClientBuilder.build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .host("rate-service")
                        .path("/api/rates/business-unit/"+dto.getBusinessUnit())
                        .queryParam("checkIn", dto.getCheckIn())
                        .queryParam("checkOut", dto.getCheckOut())
                        .build()
                )
                .retrieve()
                .bodyToMono(RateComplexResponse.class)
                .block();
    }

    @Override
    public BookingResponseDto getBooking(Long id) throws BadRequestException {
        var entity = getBookingEntity(id);
        return iBookingMapper.toBookingResponseDto(entity);
    }

    @Override
    public List<BookingResponseDtoList> getAllBooking(Long id) {
        var bookingList = iBookingRepository.findAllByIdAndDeleted(id, false);
        return iBookingMapper.bookingListToBookingResponseDtoList(bookingList);
    }

    @Override
    public String deleteBooking(Long id) throws BadRequestException {
        var entity = getBookingEntity(id);

        BaseResponse deleteStayMsg = deleteStay(entity.getId());

        if (deleteStayMsg != null && !deleteStayMsg.hastErrors()){

            BaseResponse deletePaymentMsg = deletePayment(entity.getId());

            if (deletePaymentMsg != null && !deletePaymentMsg.hastErrors()){
                entity.setDeleted(Boolean.TRUE);
                if (entity.getStatus().equals(EStatus.STATUS_IN_PROCESS))
                    entity.setStatus(EStatus.STATUS_CANCELLED);
                var entitySaved = iBookingRepository.save(entity);
                var entityDeleted = DeletedEntity.builder()
                        .idBooking(entitySaved.getId())
                        .build();
                var savedDeletedEntity = iDeletedRepository.save(entityDeleted);

                log.info("Booking has been deleted: {}", entitySaved);
                log.info("New Entity Save (DeleteEntity): {}", savedDeletedEntity);
                log.info("The stay has been deleted.");
                return "¡Booking has been deleted!";
            }else{
                log.info("Error when trying to delete the payment: {}", deletePaymentMsg.errorMessage());
                throw new IllegalArgumentException("Service communication error: " + Arrays.toString(deletePaymentMsg.errorMessage()));
            }
        }else{
            log.info("Error when trying to delete the stay: {}", deleteStayMsg.errorMessage());
            throw new IllegalArgumentException("Service communication error: " + Arrays.toString(deleteStayMsg.errorMessage()));
        }
    }

    private BaseResponse deleteStay(Long id) {
        return this.webClientBuilder.build()
                .delete()
                .uri("lb://availability-service/api/availabilities/" + id)
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
    }

    private BaseResponse deletePayment(Long id) {
        return this.webClientBuilder.build()
                .delete()
                .uri("lb://payment-service/api/payments/" + id)
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
    }

    public BookingEntity getBookingEntity(Long id) throws BadRequestException {
        if (iBookingRepository.existsByIdAndDeleted(id, false))
        //if (iBookingRepository.existsById(id))
            return iBookingRepository.getReferenceById(id);
        else
            throw new BadRequestException("Invalid Booking id");
    }
}