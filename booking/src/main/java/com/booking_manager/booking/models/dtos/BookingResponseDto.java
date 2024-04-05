package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingResponseDto {
    private Long id;
    private LocalDate creationDate;
    private Long unit;
    private Long businessUnit;
    private int amountOfPeople;
    private LocalDate checkIn; //1
    private LocalDate checkOut;//2
    private EStatus status;
    private Double costPerNight;
    private Double partialPayment;
    private int percent;
    private Double debit;
    private Double finalTotalAmount;
    private Double totalAmount;
    private EStatus paymentStatus;
    private String couponCode;
    private List<Long> serviceIdList;
    private ServiceTotalAmountDto services;
    @Override
    public String toString() {
        return "RESERVATION ID: " + this.id + "\n" +
                "Creation Date: " + this.creationDate + "\n" +
                "Unit id: " + this.unit + "\n" +
                "Business Unit id: " + this.businessUnit + "\n" +
                "Check-in: " + this.checkIn + "\n" +
                "Check-out: " + this.checkOut + "\n" +
                "Amount of people: " + this.amountOfPeople + "\n" +
                "Service list: " + this.serviceIdList;
    }
}
