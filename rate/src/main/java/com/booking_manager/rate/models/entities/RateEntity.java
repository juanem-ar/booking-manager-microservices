package com.booking_manager.rate.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "rates")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE rates SET deleted = true WHERE id=?")
public class RateEntity {
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

    @NotNull(message = "Rate is required")
    private Double rate;

    @NotNull(message = "Amount of people is required")
    @Column(name = "business_unit_id")
    private Long businessUnit;

    @NotNull(message = "Rental unit id people is required")
    @Column(name = "rental_unit_id")
    private Long rentalUnit;

    @NotNull(message = "Amount of people is required")
    @Column(name = "amount_of_people")
    @Min(1)
    @Max(9)
    private int amountOfPeople;

    @ManyToOne(fetch = FetchType.EAGER, optional = false )
    @JoinColumn(name = "season_id", updatable = false)
    private SeasonEntity season;
}
