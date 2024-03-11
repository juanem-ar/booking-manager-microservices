package com.booking_manager.payment.mappers;

import com.booking_manager.payment.models.dtos.PaymentRequestDto;
import com.booking_manager.payment.models.dtos.PaymentResponseDto;
import com.booking_manager.payment.models.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IPaymentMapper {
    PaymentEntity toEntity(PaymentRequestDto dto);
    List<PaymentResponseDto> toPaymentResponseDtoList(List<PaymentEntity> entityList);
    PaymentResponseDto toPaymentResponseDto(PaymentEntity entity);
}
