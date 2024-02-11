package com.booking_manager.business_unit.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessUnitRequestDto {

    @Size(min = 3, max = 100)
    @NotNull(message = "Business unit's name is required.")
    private String name;

    @Size(min = 3, max = 250)
    @NotNull(message = "Business unit's address is required.")
    private String address;

    @Pattern(regexp = "^[+][1-9]{2,3}[1-9]\\d{8,9}$", message = "Format: (+) + Area code + phone number. Without spaces and special characters")
    private String phoneNumber;

    @NotNull(message = "Owner's name is required.")
    private String owner;
}
