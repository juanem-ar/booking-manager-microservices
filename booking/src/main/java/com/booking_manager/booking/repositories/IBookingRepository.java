package com.booking_manager.booking.repositories;

import com.booking_manager.booking.models.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findAllByDeleted(boolean deleted);
    boolean existsByIdAndDeleted(long id, boolean deleted);
}
