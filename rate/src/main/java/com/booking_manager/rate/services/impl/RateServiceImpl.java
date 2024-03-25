package com.booking_manager.rate.services.impl;

import com.booking_manager.rate.mappers.IRateMapper;
import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;
import com.booking_manager.rate.repositories.IRateRepository;
import com.booking_manager.rate.repositories.ISeasonRepository;
import com.booking_manager.rate.services.IRateService;
import com.booking_manager.rate.services.ISeasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements IRateService {
    private final IRateRepository iRateRepository;
    private final IRateMapper iRateMapper;
    private final ISeasonService iSeasonService;

    @Override
    public RateResponseDto createRate(RateRequestDto dto) {
        var seasonEntity = iSeasonService.getSeasonEntityNotDeletedById(dto.getSeasonId());
        var entity = iRateMapper.toEntity(dto);
        entity.setDeleted(Boolean.FALSE);
        entity.setSeason(seasonEntity);
        var savedEntity = iRateRepository.save(entity);
        return iRateMapper.toResponseDto(savedEntity);
    }

    @Override
    public RateResponseDto getRate(Long id) {
        var entity = iRateRepository.getReferenceById(id);
        return iRateMapper.toResponseDto(entity);
    }

}
