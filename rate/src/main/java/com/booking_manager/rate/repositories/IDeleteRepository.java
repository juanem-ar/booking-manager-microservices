package com.booking_manager.rate.repositories;

import com.booking_manager.rate.models.entities.DeletedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeleteRepository extends JpaRepository<DeletedEntity,Long> {
}
