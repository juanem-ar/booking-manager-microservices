package com.booking_manager.payment.models.entities;

import com.booking_manager.payment.models.enums.EStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE payments SET deleted = true WHERE id=?")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @NotNull
    private Long bookingId;

    @NotNull(message = "Cost per night is required")
    @Column(name = "cost_per_night")
    @Min(0)
    private Double costPerNight;

    @NotNull(message = "Partial payment is required")
    @Column(name = "partial_payment")
    @Min(0)
    private Double partialPayment;

    @NotNull(message = "Percent is required")
    @Min(0)
    @Max(value = 100, message = "percent max value is 100")
    private int percent;

    @NotNull(message = "Debit amount is required")
    @Min(value = 0,message = "Invalid amount")
    private Double debit;

    @NotNull(message = "Total amount is required")
    @Min(value = 0,message = "Invalid amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private EStatus status;
}
