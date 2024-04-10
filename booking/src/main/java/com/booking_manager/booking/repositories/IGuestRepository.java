package com.booking_manager.booking.repositories;

import com.booking_manager.booking.models.entities.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuestRepository extends JpaRepository<GuestEntity,Long> {
    GuestEntity getReferenceByEmailAndDeleted(String email, boolean b);
    GuestEntity getReferenceByIdAndDeleted(Long guestId, boolean b);
}
