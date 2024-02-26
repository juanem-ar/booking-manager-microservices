package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingResponseDto {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private Long unit;
    private int amountOfPeople;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double costPerNight;
    private Double partialPayment;
    private int percent;
    private Double debit;
    private Double totalAmount;
    private EStatus status;
    @Override
    public String toString() {
        return "RESERVATION ID: " + this.id + "\n" +
                "Unit id: " + this.getUnit() + "\n" +
                "Amount of people: " + this.amountOfPeople + "\n" +
                "Check-in: " + this.checkIn + "\n" +
                "check-out: " + this.checkOut + "\n" + "\n"+

                "COST DETAIL: " + "\n" +
                "Cost per night: " + this.costPerNight + "\n" +
                "Payment percent: %" + this.percent + "\n" +
                "Total: " + this.totalAmount + "\n\n" +

                "PAYED: " + this.partialPayment + "\n" +
                "DEBIT: " + this.debit + "\n\n";
    }
}
