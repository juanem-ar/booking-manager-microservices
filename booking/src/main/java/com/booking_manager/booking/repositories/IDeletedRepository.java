package com.booking_manager.booking.repositories;

import com.booking_manager.booking.models.entities.DeletedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeletedRepository extends JpaRepository<DeletedEntity, Long> {
}
