package com.booking_manager.business_unit.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalUnitRequestDto {

    @Size(min = 1, max = 100)
    @NotNull(message = "Name is required.")
    private String name;

    @Size(min = 1, max = 200)
    @NotNull(message = "Address is required.")
    private String address;

    private String phoneNumber;

    @NotNull(message = "Description is required")
    @Size(max = 255)
    private String description;

    @NotNull(message = "Maximum of guests are required")
    @Min(value= 1 , message = "Minimum required is 1")
    private int maximumAmountOfGuests;

    @NotNull(message = "Number of bedrooms are required")
    @Min(value= 1 , message = "Minimum required is 1")
    private int numberOfBedrooms;

    @NotNull(message = "Number of rooms are required")
    @Min(value = 1,message = "Minimum required is 1")
    private int numberOfRooms;

    @NotNull(message = "Pool description is required")
    @Size(min = 6, max = 10)
    private String pool;

    @NotNull(message = "Business Unit id is required")
    private Long businessUnit;
}
