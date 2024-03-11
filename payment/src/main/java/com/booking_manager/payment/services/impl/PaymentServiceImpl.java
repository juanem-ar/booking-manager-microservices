package com.booking_manager.payment.services.impl;

import com.booking_manager.payment.mappers.IPaymentMapper;
import com.booking_manager.payment.models.dtos.BaseResponse;
import com.booking_manager.payment.models.dtos.NewPaymentRequestDto;
import com.booking_manager.payment.models.dtos.PaymentRequestDto;
import com.booking_manager.payment.models.dtos.PaymentResponseDto;
import com.booking_manager.payment.models.entities.DeletedEntity;
import com.booking_manager.payment.models.enums.EStatus;
import com.booking_manager.payment.repositories.IDeleteRepository;
import com.booking_manager.payment.repositories.IPaymentRepository;
import com.booking_manager.payment.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository iPaymentRepository;
    private final IPaymentMapper iPaymentMapper;
    private final IDeleteRepository iDeleteRepository;

    @Override
    public BaseResponse createPayment(PaymentRequestDto dto) {
        var errorList = new ArrayList<String>();
        try{
            var entity = iPaymentMapper.toEntity(dto);
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
        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }

    @Override
    public BaseResponse deletePayment(Long id) {
        var errorList = new ArrayList<String>();

        var entity = iPaymentRepository.findByBookingIdAndDeleted(id, false);
        if (entity!=null){
            entity.setDeleted(Boolean.TRUE);
            var entitySaved = iPaymentRepository.save(entity);
            var entityDeleted = DeletedEntity.builder()
                    .paymentId(entitySaved.getId())
                    .bookingId(entitySaved.getBookingId())
                    .build();
            var savedEntityDeleted = iDeleteRepository.save(entityDeleted);
            log.info("Payment has been deleted: {}", entitySaved);
            log.info("New Entity Save (DeleteEntity): {}", savedEntityDeleted);
        }else{
            errorList.add("Invalid Payment Id.");
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
        var entity = iPaymentRepository.findByBookingIdAndDeleted(dto.getBookingId(), false);
        //TODO NO MODIFICAR EL PAGO; SINO QUE HAY Q CREAR UN PAGO NUEVO. Buscar el ultimo pago
        if (entity!=null) {
            entity.setPartialPayment(entity.getPartialPayment() + dto.getPayment());
            if (dto.getPayment() < entity.getDebit())
                entity.setDebit(entity.getDebit() - dto.getPayment());
            else
                throw new BadRequestException("Incorrect payment amount.");
            if(entity.getPartialPayment().equals(entity.getTotalAmount()))
                entity.setStatus(EStatus.STATUS_CLOSED);
            var savedEntity = iPaymentRepository.save(entity);
            log.info("Payment Saved: {}", savedEntity);
            return iPaymentMapper.toPaymentResponseDto(savedEntity);
        }else{
            throw new IllegalArgumentException("Invalid Payment Id.");
        }
    }
}
