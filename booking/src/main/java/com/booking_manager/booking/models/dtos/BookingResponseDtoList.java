package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingResponseDtoList {
    private Long id;
    private LocalDate creationDate;
    private Long unit;
    private Long businessUnit;
    private int amountOfPeople;
    private List<Long> serviceIdList;
    private EStatus status;
}
