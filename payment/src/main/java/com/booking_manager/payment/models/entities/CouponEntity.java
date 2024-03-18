package com.booking_manager.payment.models.entities;

import com.booking_manager.payment.models.enums.ETypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE coupons SET deleted = true WHERE id=?")
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @NotNull(message = "Expiration date is required (YYYY-MM-dd).")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "expiration_date")
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
    @Column(name = "check_in_date_after")
    private LocalDate checkInDateAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    @Column(name = "check_out_date_before")
    private LocalDate checkOutDateBefore;

    @Min(0)
    @Max(value = 100, message = "minimum days max value is 100")
    @Column(name = "minimum_days")
    private int minimumDays;

    @NotNull(message = "Usage Limit is required")
    @Min(0)
    @Max(value = 1000, message = "usage limit max value is 100")
    @Column(name = "usage_limit")
    private int usageLimit;

    @NotNull(message = "Usage count is required")
    @Column(name = "usage_count")
    @Max(value = 1000, message = "usage count max value is 100")
    private int usageCount;
}
