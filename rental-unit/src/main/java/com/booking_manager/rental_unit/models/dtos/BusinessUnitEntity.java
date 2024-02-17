package com.booking_manager.rental_unit.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessUnitEntity {
    private Long id;
    private Boolean deleted;
    private String name;
    private String address;
    private String phoneNumber;
}
