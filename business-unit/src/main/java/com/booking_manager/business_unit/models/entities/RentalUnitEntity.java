package com.booking_manager.business_unit.models.entities;

import com.booking_manager.business_unit.models.enums.EPool;
import com.booking_manager.business_unit.models.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false )
    @JoinColumn(name = "business_unit_id", updatable = false)
    @JsonIgnore
    private BusinessUnitEntity businessUnit;
}
