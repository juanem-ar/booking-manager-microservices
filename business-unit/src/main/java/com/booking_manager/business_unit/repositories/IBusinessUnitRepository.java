package com.booking_manager.business_unit.repositories;

import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBusinessUnitRepository extends JpaRepository<BusinessUnitEntity, Long> {
   boolean existsByName(String name);
}
