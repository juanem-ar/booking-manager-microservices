package com.booking_manager.business_unit.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseDto {
    private Long id;
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String title;
    private String description;
    private Double price;
}
