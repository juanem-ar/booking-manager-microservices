package com.booking_manager.rate.models.dtos;

import com.booking_manager.rate.models.enums.ESeasonType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeasonRequestDto {

    @NotNull(message = "Title is required.")
    @Size(min = 3, max = 100)
    private String title;

    @NotNull(message = "Season type is required.")
    @Enumerated(EnumType.STRING)
    @Column(name = "season_type")
    private ESeasonType seasonType;

    private String description;

    @NotNull(message = "Business Unit Id is required.")
    private Long businessUnit;

    @NotNull(message = "Start date is required.")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    @Column(name = "end_date")
    private LocalDate endDate;
}
