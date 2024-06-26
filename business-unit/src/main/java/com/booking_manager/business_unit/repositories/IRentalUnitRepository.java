package com.booking_manager.business_unit.repositories;

import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRentalUnitRepository extends JpaRepository<RentalUnitEntity, Long> {
    List<RentalUnitEntity> findAllByBusinessUnitIdAndDeleted(Long id, boolean b);
    RentalUnitEntity getReferenceByIdAndDeleted(Long id, boolean b);
    boolean existsByIdAndDeleted(Long object, boolean deleted);
    boolean existsByNameAndBusinessUnitIdAndDeleted(String name, Long businessUnit, boolean b);
}
