package com.booking_manager.business_unit.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE services SET deleted= true WHERE id=?")
public class ServicesEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Size(min = 3, max = 40)
    @NotNull(message = "Name code is required.")
    private String title;

    private String description;

    @NotNull(message = "Price is required.")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false )
    @JoinColumn(name = "business_unit_id", updatable = false)
    @JsonIgnore
    private BusinessUnitEntity businessUnit;
}
