package com.booking_manager.business_unit.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessUnitResponseDto {
    private Long id;
    private Boolean deleted = Boolean.FALSE;
    private String name;
    private String address;
    private String phoneNumber;
    private String owner;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
