package com.booking_manager.payment.mappers;

import com.booking_manager.payment.models.dtos.PaymentRequestDto;
import com.booking_manager.payment.models.dtos.PaymentResponseDto;
import com.booking_manager.payment.models.entities.CouponEntity;
import com.booking_manager.payment.models.entities.PaymentEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T15:19:38-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class IPaymentMapperImpl implements IPaymentMapper {

    @Override
    public PaymentEntity toEntity(PaymentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentEntity.PaymentEntityBuilder paymentEntity = PaymentEntity.builder();

        paymentEntity.bookingId( dto.getBookingId() );
        paymentEntity.costPerNight( dto.getCostPerNight() );
        paymentEntity.partialPayment( dto.getPartialPayment() );
        paymentEntity.percent( dto.getPercent() );
        paymentEntity.debit( dto.getDebit() );
        paymentEntity.totalAmount( dto.getTotalAmount() );

        return paymentEntity.build();
    }

    @Override
    public List<PaymentResponseDto> toPaymentResponseDtoList(List<PaymentEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PaymentResponseDto> list = new ArrayList<PaymentResponseDto>( entityList.size() );
        for ( PaymentEntity paymentEntity : entityList ) {
            list.add( toPaymentResponseDto( paymentEntity ) );
        }

        return list;
    }

    @Override
    public PaymentResponseDto toPaymentResponseDto(PaymentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PaymentResponseDto.PaymentResponseDtoBuilder paymentResponseDto = PaymentResponseDto.builder();

        paymentResponseDto.code( entityCouponIdCode( entity ) );
        paymentResponseDto.id( entity.getId() );
        paymentResponseDto.bookingId( entity.getBookingId() );
        paymentResponseDto.deleted( entity.getDeleted() );
        paymentResponseDto.creationDate( entity.getCreationDate() );
        paymentResponseDto.updateDate( entity.getUpdateDate() );
        paymentResponseDto.costPerNight( entity.getCostPerNight() );
        paymentResponseDto.partialPayment( entity.getPartialPayment() );
        paymentResponseDto.percent( entity.getPercent() );
        paymentResponseDto.debit( entity.getDebit() );
        paymentResponseDto.finalTotalAmount( entity.getFinalTotalAmount() );
        paymentResponseDto.totalAmount( entity.getTotalAmount() );
        paymentResponseDto.status( entity.getStatus() );

        return paymentResponseDto.build();
    }

    private String entityCouponIdCode(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }
        CouponEntity couponId = paymentEntity.getCouponId();
        if ( couponId == null ) {
            return null;
        }
        String code = couponId.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }
}
