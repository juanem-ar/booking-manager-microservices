package com.booking_manager.booking.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicesEntity {
    private Long id;
    private Boolean deleted = Boolean.FALSE;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String title;
    private String description;
    private Double price;
}
