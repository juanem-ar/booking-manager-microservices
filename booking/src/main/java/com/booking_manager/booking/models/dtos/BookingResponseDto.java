package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingResponseDto {
    private Long id;
    private Long unit;
    private Long businessUnit;
    private int amountOfPeople;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private EStatus status;
    private Double costPerNight;
    private Double partialPayment;
    private int percent;
    private Double debit;
    private Double totalAmount;
    private EStatus paymentStatus;
    private String couponCode;
    @Override
    public String toString() {
        return "RESERVATION ID: " + this.id + "\n" +
                "Unit id: " + this.getUnit() + "\n" +
                "Amount of people: " + this.amountOfPeople + "\n" +
                "Check-in: " + this.checkIn + "\n" +
                "check-out: " + this.checkOut + "\n";
    }
}
