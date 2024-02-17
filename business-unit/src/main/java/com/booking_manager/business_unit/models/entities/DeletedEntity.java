package com.booking_manager.business_unit.models.entities;

import com.booking_manager.business_unit.models.enums.EDeletedEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "soft_deleted")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DeletedEntity extends Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private EDeletedEntity eDeletedEntity;
}
