package com.booking_manager.availability.repositories;

import com.booking_manager.availability.models.entities.StayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IStayRepository extends JpaRepository<StayEntity, Long> {
    List<StayEntity> findAllByDeletedAndRentalUnitId(boolean b, Long id);
    List<StayEntity> findAllByBookingIdAndDeleted(Long id, boolean b);

    boolean existsByCheckInLessThanAndCheckOutGreaterThanAndDeletedAndRentalUnitId(LocalDate checkIn, LocalDate checkOut, boolean b, Long rentalUnitId);

    StayEntity findByBookingIdAndDeleted(Long id, boolean b);
    StayEntity findByIdAndDeleted(Long id, boolean b);
}
