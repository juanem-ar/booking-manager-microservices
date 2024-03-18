package com.booking_manager.payment.models.dtos;

import com.booking_manager.payment.models.enums.ETypes;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CouponResponseDto {
    private Long id;
    private Boolean deleted;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private LocalDate expirationDate;
    private String code;
    private ETypes type;
    private Double amount;
    private LocalDate checkInDateAfter;
    private LocalDate checkOutDateBefore;
    private int minimumDays;
    private int usageLimit;
    private int usageCount;
}
