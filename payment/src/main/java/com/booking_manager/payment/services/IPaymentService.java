package com.booking_manager.payment.services;

import com.booking_manager.payment.models.dtos.*;

import java.util.List;

public interface IPaymentService {
    ComplexResponse createPayment(PaymentRequestDto dto);

    BaseResponse deletePayment(Long id);

    List<PaymentResponseDto> getAllPaymentsByBookingId(Long bookingId);

    PaymentResponseDto savePayment(NewPaymentRequestDto dto) throws Exception;
}
