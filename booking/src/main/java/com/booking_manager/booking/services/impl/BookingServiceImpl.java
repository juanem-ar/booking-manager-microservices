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

import java.util.ArrayList;
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
        RentalUnitComplexReponse rentalUnitResponse = getRentalUnitStatus(dto);
        ServiceTotalAmountDto totalAmountOfServices = null;
        boolean bookingWithServices = false;

        if (dto.getServices() != null) {
            totalAmountOfServices = getTotalAmountOfServices(rentalUnitResponse, dto);
            bookingWithServices = true;
        }

        if (rentalUnitResponse != null && !rentalUnitResponse.getBaseResponse().hastErrors()){

            var entity = iBookingMapper.toEntity(dto);
            entity.setDeleted(Boolean.FALSE);
            entity.setStatus(EStatus.STATUS_IN_PROCESS);
            if (totalAmountOfServices != null) {
                entity.setServiceIdList(totalAmountOfServices.getServiceIdList());
            }
            var entitySaved = iBookingRepository.save(entity);

            try{
                BaseResponse savedStay = checkAvailabilityAndSaveStay(dto, entitySaved.getId());

                if (savedStay != null && !savedStay.hastErrors()){

                    if (!bookingWithServices)
                        setTotalAmount(dto);
                    else
                        setTotalAmount(dto, totalAmountOfServices.getTotalAmount());

                    var response = savePaymentAndReturnBookingResponse(dto, entitySaved);
                    response.setServices(totalAmountOfServices);
                    return response;
                }else{
                    log.info("Error when trying to save the stay: {}", savedStay.errorMessage());
                    throw new IllegalArgumentException("Service communication error: " + Arrays.toString(savedStay.errorMessage()));
                }
            }catch (Exception e){
                deleteStayByBookingId(entitySaved.getId());
                //TODO mejorar esta toma del error. Se precisa mostrar los errores q arrastran las listas de errores.
                throw new IllegalArgumentException("Service communication error: " + e.getMessage());
            }
        }else{
            log.info("Error when trying to validate rental unit status: {}", rentalUnitResponse.getBaseResponse().errorMessage());
            throw new IllegalArgumentException("Service communication error: " + Arrays.toString(rentalUnitResponse.getBaseResponse().errorMessage()));
        }
    }

    private ServiceTotalAmountDto getTotalAmountOfServices(RentalUnitComplexReponse rentalUnitResponse, BookingRequestDto dto) {
        var totalAmount = 0.0;
        List<ServicesPriceDto> servicesList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        List<Long> servicesIdList = new ArrayList<>();
        boolean serviceFound = false;

        for (ServicesPriceDto serviceDTO: dto.getServices()) {
            serviceFound = false;
            var serviceId = serviceDTO.getId();

            for (ServicesEntity serviceRU: rentalUnitResponse.getRentalUnit().getServicesList()) {
                if (serviceRU.getId().equals(serviceDTO.getId())){
                    totalAmount+=serviceRU.getPrice();
                    serviceFound = true;
                    servicesList.add(serviceDTO);
                    servicesIdList.add(serviceDTO.getId());
                    break;
                }
            }
            if(!serviceFound){
                errorList.add("The service id " + serviceId + " was not added.");
            }
        }
        return ServiceTotalAmountDto.builder().totalAmount(totalAmount).services(servicesList).serviceIdList(servicesIdList).hastErrors(errorList).build();
    }

    private BookingResponseDto savePaymentAndReturnBookingResponse(BookingRequestDto dto, BookingEntity entitySaved) {
        PaymentComplexResponseBySave savedPayment = savePayment(dto, entitySaved.getId());

        if (savedPayment.getBaseResponse() != null && !savedPayment.getBaseResponse().hastErrors()) {
            var paymentResponse = savedPayment.getObject();

            //TODO EL ESTADO ACEPTADO DEBE SER ESTABLECIDO CUANDO SE CONFIRMA EL PAGO
            entitySaved.setStatus(EStatus.STATUS_ACCEPTED);
            //todo aca salia "entity" como parametro
            iBookingRepository.save(entitySaved);
            var response = settingAdittionalPropertiesToBookingResponseDto(dto, entitySaved, paymentResponse);
            //TODO implementar envios de emails con kafka
            log.info("Booking created: {}", entitySaved);
            return response;
        }else{
            log.info("Error when trying to save the payment: {}", savedPayment.getBaseResponse().errorMessage());
            throw new IllegalArgumentException("Service communication error: " + Arrays.stream(savedPayment.getBaseResponse().errorMessage()).iterator().toString().toString());
        }
    }

    private BookingResponseDto settingAdittionalPropertiesToBookingResponseDto(BookingRequestDto dto, BookingEntity entitySaved, PaymentResponseDto paymentResponse) {
        var response = iBookingMapper.toBookingResponseDto(entitySaved);
        response.setCheckIn(dto.getCheckIn());
        response.setCheckOut(dto.getCheckOut());
        response.setCostPerNight(paymentResponse.getCostPerNight());
        response.setPartialPayment(paymentResponse.getPartialPayment());
        response.setPercent(paymentResponse.getPercent());
        response.setDebit(paymentResponse.getDebit());
        response.setTotalAmount(paymentResponse.getTotalAmount());
        response.setPaymentStatus(paymentResponse.getStatus());
        response.setCouponCode(dto.getCode());
        return response;
    }

    private void setTotalAmount(BookingRequestDto dto) {
        RateComplexResponse rateServiceResponse = getTotalAmount(dto);
        if (rateServiceResponse != null && !rateServiceResponse.getBaseResponse().hastErrors()){
            //Se establece el monto total del request
            dto.setTotalAmount(rateServiceResponse.getTotalAmount());
        }else{
            throw new IllegalArgumentException("Rate Service communication error: " + Arrays.toString(rateServiceResponse.getBaseResponse().errorMessage()));
        }
    }
    private void setTotalAmount(BookingRequestDto dto, Double totalAmountOfServices) {
        RateComplexResponse rateServiceResponse = getTotalAmount(dto);
        if (rateServiceResponse != null && !rateServiceResponse.getBaseResponse().hastErrors()){
            //Se establece el monto total del request
            dto.setTotalAmount(rateServiceResponse.getTotalAmount() + totalAmountOfServices);
        }else{
            throw new IllegalArgumentException("Rate Service communication error: " + Arrays.toString(rateServiceResponse.getBaseResponse().errorMessage()));
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

    private StayComplexResponseByGet getStayByBookingId(Long bookingId) {
        return this.webClientBuilder.build()
                .get()
                .uri("lb://availability-service/api/availabilities/booking/" + bookingId)
                .retrieve()
                .bodyToMono(StayComplexResponseByGet.class)
                .block();
    }

    private RentalUnitComplexReponse getRentalUnitStatus(BookingRequestDto dto) {
        return this.webClientBuilder.build()
                .get()
                .uri("lb://business-service/api/rental-units/available/" + dto.getUnit())
                .retrieve()
                .bodyToMono(RentalUnitComplexReponse.class)
                .block();
    }

    private PaymentComplexResponseBySave savePayment(BookingRequestDto dto, Long bookingId) {
        return this.webClientBuilder.build()
                .post()
                .uri("lb://payment-service/api/payments/booking/"+ bookingId)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(PaymentComplexResponseBySave.class)
                .block();
    }

    private PaymentComplexResponseByGet getPaymentListByBookingId(Long bookingId){
        return this.webClientBuilder.build()
                .get()
                .uri("lb://payment-service/api/payments/booking/"+bookingId)
                .retrieve()
                .bodyToMono(PaymentComplexResponseByGet.class)
                .block();
    }

    private RateComplexResponse getTotalAmount(BookingRequestDto dto) {
        return this.webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .host("rate-service")
                        .path("/api/rates/business-unit/"+dto.getBusinessUnit())
                        .queryParam("rentalUnitId", dto.getUnit())
                        .queryParam("checkIn", dto.getCheckIn())
                        .queryParam("checkOut", dto.getCheckOut())
                        .build()
                )
                .retrieve()
                .bodyToMono(RateComplexResponse.class)
                .block();
    }

    @Override
    public BookingFullResponseDto getBooking(Long id) throws BadRequestException {
        var entity = getBookingEntity(id);

        PaymentComplexResponseByGet paymentResponse = getPaymentListByBookingId(id);
        if (paymentResponse.getBaseResponse() != null && !paymentResponse.getBaseResponse().hastErrors()){

            StayComplexResponseByGet stayResponse = getStayByBookingId(id);

            if (stayResponse.getBaseResponse() != null && !stayResponse.getBaseResponse().hastErrors()) {

                var mappedEntity = iBookingMapper.toBookingResponseDtoList(entity);
                return BookingFullResponseDto.builder().booking(mappedEntity).paymentList(paymentResponse.getPaymentList()).stay(stayResponse.getStay()).build();

            }else{
                log.info("Error when trying to get the stay: {}", Arrays.toString(stayResponse.getBaseResponse().errorMessage()));
                throw new IllegalArgumentException("Availability Service communication error: " + Arrays.stream(stayResponse.getBaseResponse().errorMessage()));
            }
        }else{
            log.info("Error when trying to get the payment: {}", Arrays.toString(paymentResponse.getBaseResponse().errorMessage()));
            throw new IllegalArgumentException("Payment service communication error: " + Arrays.stream(paymentResponse.getBaseResponse().errorMessage()));
        }
    }

    @Override
    public List<BookingResponseDtoList> getAllBookingByRentalUnit(Long id) {
        var bookingList = iBookingRepository.findAllByUnitAndDeleted(id, false);
        return iBookingMapper.bookingListToBookingResponseDtoList(bookingList);
    }

    @Override
    public String deleteBooking(Long id) throws BadRequestException {
        var entity = getBookingEntity(id);

        BaseResponse deleteStayMsg = deleteStayByBookingId(entity.getId());

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

    private BaseResponse deleteStayByBookingId(Long id) {
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