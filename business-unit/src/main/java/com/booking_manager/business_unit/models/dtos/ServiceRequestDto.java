package com.booking_manager.business_unit.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDto {
    @Size(min = 3, max = 40)
    @NotNull(message = "Name code is required.")
    private String title;
    private String description;
    @NotNull(message = "Price is required.")
    private Double price;
    @NotNull(message = "Business Unit id is required.")
    private Long businessUnitId;
}
