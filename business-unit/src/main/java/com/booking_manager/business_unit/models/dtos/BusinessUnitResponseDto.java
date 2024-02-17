package com.booking_manager.business_unit.models.dtos;

import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
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
public class BusinessUnitResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private List<RentalUnitEntity> rentalUnitList;
}
