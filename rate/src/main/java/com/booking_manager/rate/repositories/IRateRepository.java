package com.booking_manager.rate.repositories;

import com.booking_manager.rate.models.entities.RateEntity;
import com.booking_manager.rate.models.entities.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRateRepository extends JpaRepository<RateEntity, Long> {
    Boolean existsByIdAndDeleted(Long id, boolean b);
    RateEntity getReferenceByBusinessUnitAndRentalUnitAndDeletedAndSeason(Long businessUnitId, Long rentalUnitId, boolean b, SeasonEntity season);
}
