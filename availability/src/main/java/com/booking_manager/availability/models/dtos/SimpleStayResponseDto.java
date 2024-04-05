package com.booking_manager.availability.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleStayResponseDto {
    private Long id;
    private Boolean deleted;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
