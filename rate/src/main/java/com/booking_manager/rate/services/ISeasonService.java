package com.booking_manager.rate.services;

import com.booking_manager.rate.models.dtos.SeasonRequestDto;
import com.booking_manager.rate.models.dtos.SeasonResponseDto;
import com.booking_manager.rate.models.entities.SeasonEntity;

import java.util.List;

public interface ISeasonService {
    SeasonResponseDto createSeason(SeasonRequestDto dto);
    SeasonResponseDto getSeason(Long id);
    String deleteSeason(Long id);
    SeasonResponseDto editSeason(Long id, SeasonRequestDto dto);
    SeasonEntity getSeasonEntityNotDeletedById(Long id);
    List<SeasonResponseDto> getAllSeasonByBusinessUnitId(Long id);
}
