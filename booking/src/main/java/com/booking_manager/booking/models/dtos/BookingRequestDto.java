package com.booking_manager.booking.models.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    @NotNull(message = "Business Unit Id is required")
    private Long businessUnit;

    @NotNull(message = "Rental Unit Id is required")
    private Long unit;

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

    private List<ServicesPriceDto> services;

    private GuestRequestDto guest;

    public void validatePeriod(LocalDate checkIn, LocalDate checkOut) throws BadRequestException {
        if (checkIn.isAfter(checkOut))
            throw new BadRequestException("Invalid check-in date");
    }
}
