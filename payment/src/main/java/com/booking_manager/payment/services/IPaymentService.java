package com.booking_manager.payment.services;

import com.booking_manager.payment.models.dtos.*;
import org.apache.coyote.BadRequestException;

public interface IPaymentService {
    ComplexResponseBySave createPayment(BookingRequestDto dto, Long id) throws BadRequestException;

    BaseResponse deletePayment(Long id);

    PaymentComplexResponseByGet getAllPaymentsByBookingId(Long bookingId);

    PaymentResponseDto savePayment(NewPaymentRequestDto dto) throws Exception;
}
