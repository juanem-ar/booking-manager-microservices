package com.booking_manager.rate.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateResponseDto {
    private Long id;
    private Boolean deleted;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private Double rate;
    private Long businessUnit;
    private Long rentalUnit;
    private int amountOfPeople;
}
