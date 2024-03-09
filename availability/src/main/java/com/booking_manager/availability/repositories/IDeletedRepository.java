package com.booking_manager.availability.repositories;

import com.booking_manager.availability.models.entities.DeletedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeletedRepository extends JpaRepository<DeletedEntity, Long> {
}
