package com.booking_manager.rate.services.impl;

import com.booking_manager.rate.mappers.ISeasonMapper;
import com.booking_manager.rate.models.dtos.SeasonRequestDto;
import com.booking_manager.rate.models.dtos.SeasonResponseDto;
import com.booking_manager.rate.models.entities.DeletedEntity;
import com.booking_manager.rate.models.entities.SeasonEntity;
import com.booking_manager.rate.models.enums.EEntityTypes;
import com.booking_manager.rate.repositories.IDeleteRepository;
import com.booking_manager.rate.repositories.ISeasonRepository;
import com.booking_manager.rate.services.ISeasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SeasonServiceImpl implements ISeasonService {
    private final ISeasonMapper iSeasonMapper;
    private final ISeasonRepository iSeasonRepository;
    private final IDeleteRepository iDeleteRepository;
    @Override
    public SeasonResponseDto createSeason(SeasonRequestDto dto) {
        var entity = iSeasonMapper.toEntity(dto);
        entity.setDeleted(Boolean.FALSE);
        var savedEntity = iSeasonRepository.save(entity);
        return iSeasonMapper.toResponseDto(savedEntity);
    }

    @Override
    public SeasonResponseDto getSeason(Long id) {
        var entity = getSeasonEntityNotDeletedById(id);
        return iSeasonMapper.toResponseDto(entity);
    }

    @Override
    public String deleteSeason(Long id) {
        var entity = getSeasonEntityNotDeletedById(id);
        entity.setDeleted(Boolean.TRUE);
        var savedEntity = iSeasonRepository.save(entity);
        var deletedEntity = DeletedEntity.builder()
                .idEntity(entity.getId())
                .entityTypes(EEntityTypes.SEASON)
                .build();
        var savedDeletedEntity = iDeleteRepository.save(deletedEntity);
        log.info("Deleted Season Entity: {}", savedEntity);
        log.info("new Deleted Entity added: {}", savedDeletedEntity);
        return "Deleted season.";
    }

    @Override
    public SeasonResponseDto editSeason(Long id, SeasonRequestDto dto) {
        var entity = getSeasonEntityNotDeletedById(id);
        var mappedEntity = iSeasonMapper.updateEntity(dto, entity);
        var savedEntity = iSeasonRepository.save(mappedEntity);
        log.info("Updated Season Entity: {}", savedEntity);
        return iSeasonMapper.toResponseDto(savedEntity);
    }

    public SeasonEntity getSeasonEntityNotDeletedById(Long id){
        var existsEntity = iSeasonRepository.existsByIdAndDeleted(id, false);
        if (existsEntity) {
            return  iSeasonRepository.getReferenceById(id);
        }else{
            throw new IllegalArgumentException("Invalid Season id.");
        }
    }

    @Override
    public List<SeasonResponseDto> getAllSeasonByBusinessUnitId(Long id) {
        var entityList = iSeasonRepository.findAllByBusinessUnitAndDeleted(id, false);
        return iSeasonMapper.toResponseDtoList(entityList);
    }

    @Override
    public SeasonEntity getSeasonEntityNotDeletedByBusinessUnitIdAndDate(Long businessUnitId, LocalDate date) {
        var entity = iSeasonRepository.findByBusinessUnitAndDeletedAndStartDateLessThanAndEndDateGreaterThanOrOrStartDateEqualsOrEndDateEquals (businessUnitId, false, date , date, date, date);
        if (entity != null){
            return entity;
        }else{
            throw new IllegalArgumentException("Date without prices.");
        }

    }
}
