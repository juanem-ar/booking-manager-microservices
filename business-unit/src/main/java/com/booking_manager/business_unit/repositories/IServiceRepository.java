package com.booking_manager.business_unit.repositories;

import com.booking_manager.business_unit.models.entities.ServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServiceRepository extends JpaRepository<ServicesEntity, Long> {
    ServicesEntity getReferenceByIdAndDeleted(Long id, boolean b);

    ServicesEntity getReferenceByTitleAndDeleted(String title, boolean b);

    List<ServicesEntity> findAllByBusinessUnitIdAndDeletedOrderById(Long businessUnit, boolean b);

    Boolean existsByTitleAndDeleted(String title, boolean b);
}
