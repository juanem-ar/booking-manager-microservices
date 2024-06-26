package com.booking_manager.payment.services.impl;

import com.booking_manager.payment.mappers.IPaymentMapper;
import com.booking_manager.payment.models.dtos.*;
import com.booking_manager.payment.models.entities.CouponEntity;
import com.booking_manager.payment.models.entities.DeletedEntity;
import com.booking_manager.payment.models.entities.PaymentEntity;
import com.booking_manager.payment.models.enums.EStatus;
import com.booking_manager.payment.repositories.IDeleteRepository;
import com.booking_manager.payment.repositories.IPaymentRepository;
import com.booking_manager.payment.services.ICouponService;
import com.booking_manager.payment.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository iPaymentRepository;
    private final IPaymentMapper iPaymentMapper;
    private final IDeleteRepository iDeleteRepository;
    private final ICouponService iCouponService;

    @Override
    public ComplexResponseBySave createPayment(BookingRequestDto dto, Long bookingId) throws BadRequestException {
        var errorList = new ArrayList<String>();

        boolean existsCouponEntity = false;
        CouponEntity couponEntity = new CouponEntity();

        long daysDuration = DAYS.between(dto.getCheckIn(), dto.getCheckOut());
        double totalAmount = dto.getTotalAmount();
        double costPerNight = totalAmount / daysDuration;
        double partialPayment = dto.getPartialPayment();

        if (dto.getCode()!=null){
            var couponResponse = iCouponService.applyDiscount(dto.getBusinessUnit(), totalAmount, dto.getCheckIn(), dto.getCheckOut(), daysDuration, dto.getCode());
            existsCouponEntity = true;
            totalAmount = couponResponse.getTotalAmount();
            couponEntity = couponResponse.getCoupon();
        }

        int percent = (int) ((partialPayment / totalAmount)*100);
        double debit = totalAmount - partialPayment;

        var entity = PaymentEntity.builder()
                .bookingId(bookingId)
                .partialPayment(partialPayment)
                .percent(percent)
                .deleted(Boolean.FALSE)
                .debit(debit)
                .costPerNight(costPerNight)
                .totalAmount(dto.getTotalAmount())
                .finalTotalAmount(totalAmount)
                .build();
        try{
            if (existsCouponEntity)
                entity.setCouponId(couponEntity);
            if(entity.getDebit()>0)
                entity.setStatus(EStatus.STATUS_OPEN);
            else{
                entity.setStatus(EStatus.STATUS_CLOSED);
            }
            var savedEntity = iPaymentRepository.save(entity);
            log.info("Payment Saved: {}", savedEntity);
        }catch (Exception e){
            errorList.add("Error when trying to save the payment. Payment Service is not available: "+ "\n" + e.getMessage());
        }
        var resultWithErrors = ComplexResponseBySave.builder().baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();
        var resultWithoutErrors = ComplexResponseBySave.builder().object(iPaymentMapper.toPaymentResponseDto(entity)).baseResponse(new BaseResponse(null)).build();
        return errorList.size() > 0 ?  resultWithErrors : resultWithoutErrors;
    }

    @Override
    public BaseResponse deletePayment(Long id) {
        var errorList = new ArrayList<String>();
        var entityList = iPaymentRepository.findAllByBookingIdAndDeleted(id, false);
        if(entityList.isEmpty()){
            errorList.add("Invalid Payment Id.");
        }else{
            for (PaymentEntity entity: entityList) {
                entity.setDeleted(Boolean.TRUE);
                var entitySaved = iPaymentRepository.save(entity);
                var entityDeleted = DeletedEntity.builder()
                        .paymentId(entitySaved.getId())
                        .bookingId(entitySaved.getBookingId())
                        .build();
                var savedEntityDeleted = iDeleteRepository.save(entityDeleted);
                log.info("Payment has been deleted: {}", entitySaved);
                log.info("New Entity Save (DeleteEntity): {}", savedEntityDeleted);
            }
        }
        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }

    @Override
    public PaymentComplexResponseByGet getAllPaymentsByBookingId(Long id) {
        List<String> errorList = new ArrayList<>();

        var entityList = iPaymentRepository.findAllByBookingIdAndDeleted(id, false);

        if(entityList.isEmpty())
            errorList.add("Invalid booking id.");

        var mappedEntity = iPaymentMapper.toPaymentResponseDtoList(entityList);
        var resultWithoutErrors = PaymentComplexResponseByGet.builder().paymentList(mappedEntity).baseResponse(new BaseResponse(null)).build();
        var resultWithErrors = PaymentComplexResponseByGet.builder().paymentList(new ArrayList<>()).baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();

        return !errorList.isEmpty() ? resultWithErrors : resultWithoutErrors;
    }

    @Override
    public PaymentResponseDto savePayment(NewPaymentRequestDto dto) throws BadRequestException {
        var lastEntity = iPaymentRepository.findFirstByBookingIdAndDeletedOrderByIdDesc(dto.getBookingId(), false);
        if (lastEntity!=null && dto.getPayment() > 0){
            double debit = lastEntity.getDebit() - dto.getPayment();
            double total = lastEntity.getTotalAmount();
            int percent = (int) (((total - debit) / total)*100);

            if (dto.getPayment() > lastEntity.getDebit())
                throw new BadRequestException("Incorrect payment amount.");

            var newEntity = PaymentEntity.builder()
                    .deleted(Boolean.FALSE)
                    .bookingId(dto.getBookingId())
                    .costPerNight(lastEntity.getCostPerNight())
                    .partialPayment(dto.getPayment())
                    .debit(debit)
                    .percent(percent)
                    .totalAmount(lastEntity.getTotalAmount())
                    .status(EStatus.STATUS_OPEN)
                    .build();

            if(debit == 0)
                newEntity.setStatus(EStatus.STATUS_CLOSED);

            var savedEntity = iPaymentRepository.save(newEntity);
            log.info("Payment Saved: {}", newEntity);
            return iPaymentMapper.toPaymentResponseDto(savedEntity);
        }else{
            throw new BadRequestException("Invalid payment.");
        }
    }
}