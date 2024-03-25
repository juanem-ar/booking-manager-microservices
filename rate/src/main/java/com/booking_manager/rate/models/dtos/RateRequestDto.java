package com.booking_manager.rate.models.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateRequestDto {
    @NotNull(message = "Rate is required")
    private Double rate;

    @NotNull(message = "Amount of people is required")
    @Column(name = "business_unit_id")
    private Long businessUnit;

    @NotNull(message = "Rental unit id people is required")
    @Column(name = "rental_unit_id")
    private Long rentalUnit;

    @NotNull(message = "Amount of people is required")
    @Column(name = "amount_of_people")
    @Min(1)
    @Max(9)
    private int amountOfPeople;

    @NotNull(message = "Season id is required")
    private Long seasonId;
}
