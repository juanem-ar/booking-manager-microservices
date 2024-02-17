package com.booking_manager.rental_unit.models.entities;

import com.booking_manager.rental_unit.models.dtos.BusinessUnitEntity;
import com.booking_manager.rental_unit.models.enums.EPool;
import com.booking_manager.rental_unit.models.enums.EStatus;
import jakarta.persistence.*;
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
@Table(name = "rental_unit")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE rental_unit SET deleted= true WHERE id=?")
public class RentalUnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean deleted = Boolean.FALSE;

    private String name;
    private String address;
    private String phoneNumber;
    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;
    private String description;
    private int maximumAmountOfGuests;
    private int numberOfBedrooms;
    private int numberOfRooms;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    @Enumerated(EnumType.STRING)
    private EPool pool;
    private Long businessUnit;
}
