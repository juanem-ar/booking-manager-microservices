package com.booking_manager.rental_unit.models.dtos;

import com.booking_manager.rental_unit.models.enums.EPool;
import com.booking_manager.rental_unit.models.enums.EStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalUnitRequestDto {

    @Size(min = 1, max = 100)
    @NotNull(message = "Name is required.")
    private String name;

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
