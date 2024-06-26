package com.booking_manager.availability.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "stays")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE stays SET deleted = true WHERE id=?")
public class StayEntity {
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

    @Column(name = "rental_unit_id")
    private Long rentalUnitId;

    @Column(name = "business_unit_id")
    private Long businessUnitId;

    @Column(name = "booking_id")
    private Long bookingId;

    @NotNull(message = "Check-in date is required (YYYY-MM-dd).")
    @Column(name = "check_in")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required (YYYY-MM-dd).")
    @Column(name = "check_out")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    private LocalDate checkOut;

    @Override
    public String toString() {
        return "\n" + "Stay Id: " + this.id + "\n" +
                "Deleted: " + this.deleted + "\n" +
                "Rental Unit Id: " + this.getRentalUnitId() + "\n" +
                "Booking Id: " + this.getBookingId() + "\n" +
                "Check-in: " + this.checkIn + "\n" +
                "Check-out: " + this.checkOut + "\n";
    }
}
