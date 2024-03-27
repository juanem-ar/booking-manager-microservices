package com.booking_manager.rate.repositories;

import com.booking_manager.rate.models.entities.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ISeasonRepository extends JpaRepository<SeasonEntity, Long> {
    Boolean existsByIdAndDeleted(Long id, boolean b);
    List<SeasonEntity> findAllByBusinessUnitAndDeleted(Long id, boolean b);
    SeasonEntity findByBusinessUnitAndDeletedAndStartDateLessThanAndEndDateGreaterThanOrOrStartDateEqualsOrEndDateEquals(Long businessUnitId, boolean b, LocalDate date, LocalDate date2, LocalDate date3, LocalDate date4);
}
