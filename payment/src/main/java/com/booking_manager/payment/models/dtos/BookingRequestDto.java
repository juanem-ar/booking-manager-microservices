package com.booking_manager.payment.models.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    @NotNull(message = "Rental Unit Id is required")
    private Long unit;
    
    @NotNull(message = "Business Unit Id is required")
    private Long businessUnit;

    @NotNull(message = "Amount of people is required")
    @Min(1)
    @Max(9)
    private int amountOfPeople;

    @NotNull(message = "Check-in date is required (YYYY-MM-dd).")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required (YYYY-MM-dd).")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    private LocalDate checkOut;

    private Double totalAmount;

    @NotNull(message = "Partial payment is required")
    @Min(0)
    private Double partialPayment;

    private String code;

    public void validatePeriod(LocalDate checkIn, LocalDate checkOut) throws BadRequestException {
        if (checkIn.isAfter(checkOut))
            throw new BadRequestException("Invalid check-in date");
    }
}
