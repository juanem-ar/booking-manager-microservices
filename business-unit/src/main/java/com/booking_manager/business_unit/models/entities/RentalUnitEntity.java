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
@Table(name = "rental_unit")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE rental_unit SET deleted= true WHERE id=?")
public class RentalUnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
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

    @Override
    public String toString() {
        return "Rental Unit: " + this.id + "\n" +
                "deleted: " + this.deleted + "\n" +
                "businessUnit: " + this.businessUnit.getId() + "\n"+
                "name: " + this.name + "\n" +
                "description: " + this.description + "\n" +
                "address: " + this.address + "\n" +
                "phoneNumber: " + this.phoneNumber + "\n"+
                "creationDate: " + this.creationDate + "\n" +
                "updateDate: " + this.updateDate + "\n" +
                "maximumAmountOfGuests: " + this.maximumAmountOfGuests + "\n"+
                "numberOfBedrooms: " + this.numberOfBedrooms + "\n" +
                "numberOfRooms: " + this.numberOfRooms + "\n" +
                "status: " + this.status + "\n"+
                "pool: " + this.pool + "\n";
    }
}
