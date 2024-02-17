package com.booking_manager.business_unit.models.entities;

import com.booking_manager.business_unit.models.enums.EStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "status")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StatusEntity extends Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private EStatus eStatus;
}
