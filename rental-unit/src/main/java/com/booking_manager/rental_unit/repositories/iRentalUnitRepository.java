package com.booking_manager.rental_unit.repositories;

import com.booking_manager.rental_unit.models.entities.RentalUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iRentalUnitRepository extends JpaRepository<RentalUnitEntity, Long> {
    List<RentalUnitEntity> findAllByBusinessUnit(Long id);
}
