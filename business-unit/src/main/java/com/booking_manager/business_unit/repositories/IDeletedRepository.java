package com.booking_manager.business_unit.repositories;

import com.booking_manager.business_unit.models.entities.DeletedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeletedRepository extends JpaRepository<DeletedEntity,Long> {
}
