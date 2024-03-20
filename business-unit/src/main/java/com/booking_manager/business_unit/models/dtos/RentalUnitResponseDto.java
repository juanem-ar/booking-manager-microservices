package com.booking_manager.business_unit.models.dtos;

import com.booking_manager.business_unit.models.enums.EPool;
import com.booking_manager.business_unit.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalUnitResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String description;
    private int maximumAmountOfGuests;
    private int numberOfBedrooms;
    private int numberOfRooms;
    private EStatus status;
    private EPool pool;
    private Long businessUnit;
    private List<ServiceResponseDto> serviceList;
}
