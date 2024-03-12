package com.booking_manager.payment.services.impl;

import com.booking_manager.payment.mappers.IPaymentMapper;
import com.booking_manager.payment.models.dtos.*;
import com.booking_manager.payment.models.entities.DeletedEntity;
import com.booking_manager.payment.models.entities.PaymentEntity;
import com.booking_manager.payment.models.enums.EStatus;
import com.booking_manager.payment.repositories.IDeleteRepository;
import com.booking_manager.payment.repositories.IPaymentRepository;
import com.booking_manager.payment.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository iPaymentRepository;
    private final IPaymentMapper iPaymentMapper;
    private final IDeleteRepository iDeleteRepository;

    @Override
    public ComplexResponse createPayment(PaymentRequestDto dto) {
        var errorList = new ArrayList<String>();
        var entity = iPaymentMapper.toEntity(dto);
        try{
            entity.setDeleted(Boolean.FALSE);
            if(dto.getDebit()!=0)
                entity.setStatus(EStatus.STATUS_OPEN);
            else
                entity.setStatus(EStatus.STATUS_CLOSED);
            var savedEntity = iPaymentRepository.save(entity);
            log.info("Payment Saved: {}", savedEntity);
        }catch (Exception e){
            errorList.add("Error when trying to save the payment. Payment Service is not available: "+ "\n" + e.getMessage());
        }
        var resultWithErrors = ComplexResponse.builder().baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();
        var resultWithoutErrors = ComplexResponse.builder().responseDto(iPaymentMapper.toPaymentResponseDto(entity)).baseResponse(new BaseResponse(null)).build();
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
    public List<PaymentResponseDto> getAllPaymentsByBookingId(Long id) {
        var entityList = iPaymentRepository.findAllByBookingIdAndDeleted(id, false);
        return iPaymentMapper.toPaymentResponseDtoList(entityList);
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
