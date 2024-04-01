package com.booking_manager.payment.services;

import com.booking_manager.payment.models.dtos.CouponRequestDto;
import com.booking_manager.payment.models.dtos.CouponResponseDto;
import com.booking_manager.payment.models.dtos.CouponResponseDtoWithEntity;
import org.apache.coyote.BadRequestException;

import java.time.LocalDate;
import java.util.List;

public interface ICouponService {
    CouponResponseDto createCoupon(CouponRequestDto dto) throws BadRequestException;

    List<CouponResponseDto> getAllCoupons(Long id);

    CouponResponseDto editCoupon(CouponRequestDto dto, Long id) throws BadRequestException;

    String deleteCoupon(Long id);

    CouponResponseDto getCouponById(Long id);

    CouponResponseDtoWithEntity applyDiscount(Long businessUnitId, Double totalAmount, LocalDate bookingCheckIn, LocalDate bookingCheckOut, Long duration, String code) throws BadRequestException;
}
