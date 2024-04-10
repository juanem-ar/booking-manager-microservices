package com.booking_manager.rate.models.entities;

import com.booking_manager.rate.models.enums.ESeasonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.time.DurationMin;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "seasons")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE seasons SET deleted = true WHERE id=?")
public class SeasonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

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
