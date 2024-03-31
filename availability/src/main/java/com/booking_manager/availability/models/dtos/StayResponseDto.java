package com.booking_manager.availability.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StayResponseDto {

    private Long id;
    private Long rentalUnitId;
    private Long businessUnitId;
    private Long bookingId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
