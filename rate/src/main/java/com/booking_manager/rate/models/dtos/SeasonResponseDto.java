package com.booking_manager.rate.models.dtos;

import com.booking_manager.rate.models.enums.ESeasonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeasonResponseDto {
    private Long id;
    private Boolean deleted;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String title;
    private ESeasonType seasonType;
    private String description;
    private Long businessUnit;
    private LocalDate startDate;
    private LocalDate endDate;
}
