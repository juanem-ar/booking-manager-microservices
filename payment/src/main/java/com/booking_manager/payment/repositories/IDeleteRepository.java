package com.booking_manager.payment.repositories;

import com.booking_manager.payment.models.entities.DeletedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeleteRepository extends JpaRepository<DeletedEntity, Long> {
}
