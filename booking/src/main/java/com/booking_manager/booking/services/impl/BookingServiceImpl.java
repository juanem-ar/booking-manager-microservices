package com.booking_manager.booking.services.impl;

import com.booking_manager.booking.mappers.IBookingMapper;
import com.booking_manager.booking.models.dtos.*;
import com.booking_manager.booking.models.entities.BookingEntity;
import com.booking_manager.booking.models.entities.DeletedEntity;
import com.booking_manager.booking.models.entities.GuestEntity;
import com.booking_manager.booking.models.enums.EStatus;
import com.booking_manager.booking.models.enums.EType;
import com.booking_manager.booking.repositories.IBookingRepository;
import com.booking_manager.booking.repositories.IDeletedRepository;
import com.booking_manager.booking.services.IBookingService;
import com.booking_manager.booking.services.IGuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
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
    private final IGuestService iGuestService;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto dto) throws Exception {
        dto.validatePeriod(dto.getCheckIn(),dto.getCheckOut());
        RentalUnitComplexReponse rentalUnitResponse = getRentalUnitStatus(dto);
        ServiceTotalAmountDto totalAmountOfServices = null;
        GuestEntity guestEntity = null;
        boolean bookingWithServices = false;

        if (dto.getServices() != null) {
            totalAmountOfServices = getTotalAmountOfServices(rentalUnitResponse, dto);
            bookingWithServices = true;
        }

        if (rentalUnitResponse != null && !rentalUnitResponse.getBaseResponse().hastErrors()){

            var entity = iBookingMapper.toEntity(dto);
            entity.setDeleted(Boolean.FALSE);
            entity.setStatus(EStatus.STATUS_IN_PROCESS);
            if(dto.getGuest() != null){
                guestEntity = iGuestService.addGuest(dto.getGuest());
                entity.setGuest(guestEntity);
            }
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
                        setTotalAmount(dto, totalAmountOfServices != null ? totalAmountOfServices.getTotalAmount() : null);

                    var response = savePaymentAndReturnBookingResponse(dto, entitySaved);
                    response.setServices(totalAmountOfServices);
                    if (guestEntity != null){
                        var guestResponse = iGuestService.addToBookingList(guestEntity.getId(),entitySaved);
                        response.setGuest(guestResponse);
                    }
                    return response;
                }else{
                    log.info("Error when trying to save the stay: ", (Object[]) (savedStay != null ? savedStay.errorMessage() : new String[0]));
                    throw new IllegalArgumentException("Service communication error: " + Arrays.toString(savedStay != null ? savedStay.errorMessage() : new String[0]));
                }
            }catch (Exception e){
                deleteStayByBookingId(entitySaved.getId());
                //TODO mejorar esta toma del error. Se precisa mostrar los errores q arrastran las listas de errores.
                throw new IllegalArgumentException("Service communication error: " + e.getMessage());
            }
        }else{
            log.info("Error when trying to validate rental unit status: {}", (Object[]) (rentalUnitResponse != null ? rentalUnitResponse.getBaseResponse().errorMessage() : new String[0]));
            throw new IllegalArgumentException("Service communication error: " + Arrays.toString(rentalUnitResponse != null ? rentalUnitResponse.getBaseResponse().errorMessage() : new String[0]));
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
            iBookingRepository.save(entitySaved);
            var response = settingAdittionalPropertiesToBookingResponseDto(dto, entitySaved, paymentResponse);
            //TODO implementar envios de emails con kafka
            //  Ingreso y Egreso. Se deberá informar al huésped, el horario de ingreso (Check-in), egreso (Check-out) y la política referente al servicio de desayuno.
            // Llevar registro manual, en un libro foliado y rubricado o electrónico, consignando entradas y salidas, donde deberá quedar asentada toda persona que ingrese al establecimiento, en calidad de pasajero, indicando: apellido y nombre, nacionalidad, procedencia, domicilio, estado civil, documento de curso legal vigente que acredite su identidad, fecha y hora de ingreso y de egreso
            //  Art. 13.- Tarjeta de registro. Se deberá confeccionar por duplicado una tarjeta de registro en la que conste el nombre, la categoría e identificación del establecimiento, fechas de entrada y salida, numero/s de habitación/es en la cual se alojó, datos personales y firma del huésped. Dicha tarjeta, tiene valor de prueba a efectos administrativos. Una copia debe ser entregada al huésped y la otra se debe conservar en el establecimiento, a fin de ser presentado ante requerimiento de la autoridad competente durante el tiempo que la reglamentación determine
            log.info("Booking created: {}", entitySaved);
            return response;
        }else{
            log.info("Error when trying to save the payment: {}", (Object[]) (savedPayment.getBaseResponse() != null ? savedPayment.getBaseResponse().errorMessage() : new String[0]));
            throw new IllegalArgumentException("Service communication error: " + Arrays.stream(savedPayment.getBaseResponse().errorMessage()).iterator().toString());
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
        response.setFinalTotalAmount(paymentResponse.getFinalTotalAmount());
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
            throw new IllegalArgumentException("Rate Service communication error: " + Arrays.toString(rateServiceResponse != null ? rateServiceResponse.getBaseResponse().errorMessage() : new String[0]));
        }
    }
    private void setTotalAmount(BookingRequestDto dto, Double totalAmountOfServices) {
        RateComplexResponse rateServiceResponse = getTotalAmount(dto);
        if (rateServiceResponse != null && !rateServiceResponse.getBaseResponse().hastErrors()){
            //Se establece el monto total del request
            dto.setTotalAmount(rateServiceResponse.getTotalAmount() + totalAmountOfServices);
        }else{
            throw new IllegalArgumentException("Rate Service communication error: " + Arrays.toString(rateServiceResponse != null ? rateServiceResponse.getBaseResponse().errorMessage() : new String[0]));
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
                log.info("Error when trying to get the stay: {}", Arrays.toString(stayResponse.getBaseResponse() != null ? stayResponse.getBaseResponse().errorMessage() : new String[0]));
                throw new IllegalArgumentException("Availability Service communication error: " + Arrays.stream(stayResponse.getBaseResponse().errorMessage()));
            }
        }else{
            log.info("Error when trying to get the payment: {}", Arrays.toString(paymentResponse.getBaseResponse() != null ? paymentResponse.getBaseResponse().errorMessage() : new String[0]));
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
                        .idEntity(entitySaved.getId())
                        .type(EType.BOOKING)
                        .build();
                var savedDeletedEntity = iDeletedRepository.save(entityDeleted);

                log.info("Booking has been deleted: {}", entitySaved);
                log.info("New Entity Save (DeleteEntity): {}", savedDeletedEntity);
                log.info("The stay has been deleted.");
                return "¡Booking has been deleted!";
            }else{
                log.info("Error when trying to delete the payment: {}", (Object[]) (deletePaymentMsg != null ? deletePaymentMsg.errorMessage() : new String[0]));
                throw new IllegalArgumentException("Service communication error: " + Arrays.toString(deletePaymentMsg != null ? deletePaymentMsg.errorMessage() : new String[0]));
            }
        }else{
            log.info("Error when trying to delete the stay: {}", (Object[]) (deleteStayMsg != null ? deleteStayMsg.errorMessage() : new String[0]));
            throw new IllegalArgumentException("Service communication error: " + Arrays.toString(deleteStayMsg != null ? deleteStayMsg.errorMessage() : new String[0]));
        }
    }

    @Override
    public BookingFullResponseDto addGuestToBookingByBookingId(GuestRequestDto dto, Long id) throws BadRequestException {
        var entity = getBookingEntity(id);
        var guestEntity = iGuestService.addGuest(dto);
        iGuestService.addToBookingList(guestEntity.getId(), entity);
        entity.setGuest(guestEntity);
        var savedEntity = iBookingRepository.save(entity);
        return getBooking(savedEntity.getId());
    }

    @Override
    public String setRealCheckIn(Long id) throws BadRequestException {
        saveRealDateTime(id, true);
        return "Real Check-in has been set up";
    }
    @Override
    public String setRealCheckOut(Long id) throws BadRequestException {
        saveRealDateTime(id, false);
        return "Real Check-out has been set up";
    }

    private void saveRealDateTime(Long id, boolean isCheckIn) throws BadRequestException {
        LocalDateTime now = LocalDateTime.now();
        var entity = getBookingEntity(id);
        if (isCheckIn)
            entity.setRealCheckIn(now);
        else
            entity.setRealCheckOut(now);
        iBookingRepository.save(entity);
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