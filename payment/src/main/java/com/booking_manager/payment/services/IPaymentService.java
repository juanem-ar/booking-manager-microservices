package com.booking_manager.payment.services;

import com.booking_manager.payment.models.dtos.BaseResponse;
import com.booking_manager.payment.models.dtos.NewPaymentRequestDto;
import com.booking_manager.payment.models.dtos.PaymentRequestDto;
import com.booking_manager.payment.models.dtos.PaymentResponseDto;

import java.util.List;

public interface IPaymentService {
    BaseResponse createPayment(PaymentRequestDto dto);

    BaseResponse deletePayment(Long id);

    List<PaymentResponseDto> getAllPaymentsByBookingId(Long bookingId);

    PaymentResponseDto savePayment(NewPaymentRequestDto dto) throws Exception;
}
