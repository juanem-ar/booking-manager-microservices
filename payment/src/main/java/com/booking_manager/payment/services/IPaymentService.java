package com.booking_manager.payment.services;

import com.booking_manager.payment.models.dtos.*;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IPaymentService {
    ComplexResponse createPayment(BookingRequestDto dto, Long id) throws BadRequestException;

    BaseResponse deletePayment(Long id);

    List<PaymentResponseDto> getAllPaymentsByBookingId(Long bookingId);

    PaymentResponseDto savePayment(NewPaymentRequestDto dto) throws Exception;
}
