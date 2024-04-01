package com.booking_manager.payment.models.dtos;

import com.booking_manager.payment.models.entities.CouponEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponResponseDtoWithEntity {
    private Double totalAmount;
    private CouponEntity coupon;
}
