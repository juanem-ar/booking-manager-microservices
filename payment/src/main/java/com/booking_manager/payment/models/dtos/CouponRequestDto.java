package com.booking_manager.payment.models.dtos;

import com.booking_manager.payment.models.enums.ETypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequestDto {

    @NotNull(message = "Expiration date is required (YYYY-MM-dd).")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expirationDate;

    @Size(min = 3, max = 10)
    @NotNull(message = "Coupon code is required.")
    private String code;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    private ETypes type;

    @NotNull(message = "Amount is required")
    private Double amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent
    private LocalDate checkInDateAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    private LocalDate checkOutDateBefore;

    @Min(0)
    @Max(value = 100, message = "minimum days max value is 100")
    private int minimumDays;

    @NotNull(message = "usage limit is required")
    @Min(0)
    @Max(value = 1000, message = "usage limit max value is 100")
    private int usageLimit;
}
