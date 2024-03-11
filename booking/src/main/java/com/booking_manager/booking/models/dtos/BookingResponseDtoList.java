package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDtoList {
    private Long id;
    private Long unit;
    private int amountOfPeople;
    private EStatus status;
}
