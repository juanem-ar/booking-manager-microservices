package com.booking_manager.rate.services.impl;

import com.booking_manager.rate.mappers.IRateMapper;
import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;
import com.booking_manager.rate.models.entities.DeletedEntity;
import com.booking_manager.rate.models.entities.RateEntity;
import com.booking_manager.rate.models.enums.EEntityTypes;
import com.booking_manager.rate.repositories.IDeleteRepository;
import com.booking_manager.rate.repositories.IRateRepository;
import com.booking_manager.rate.services.IRateService;
import com.booking_manager.rate.services.ISeasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RateServiceImpl implements IRateService {
    private final IRateRepository iRateRepository;
    private final IRateMapper iRateMapper;
    private final ISeasonService iSeasonService;
    private final IDeleteRepository iDeleteRepository;

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
        var entity = getRateEntityNotDeletedById(id);
        return iRateMapper.toResponseDto(entity);
    }

    @Override
    public String deleteRate(Long id) {
        var entity = getRateEntityNotDeletedById(id);
        entity.setDeleted(Boolean.TRUE);
        var savedEntity = iRateRepository.save(entity);
        var deletedEntity = DeletedEntity.builder()
                .idEntity(entity.getId())
                .entityTypes(EEntityTypes.RATE)
                .build();
        var savedDeletedEntity = iDeleteRepository.save(deletedEntity);
        log.info("Deleted Rate Entity: {}", savedEntity);
        log.info("new Deleted Entity added: {}", savedDeletedEntity);
        return "Deleted rate.";
    }

    public RateEntity getRateEntityNotDeletedById(Long id){
        var existsEntity = iRateRepository.existsByIdAndDeleted(id, false);
        if (existsEntity) {
            return  iRateRepository.getReferenceById(id);
        }else{
            throw new IllegalArgumentException("Invalid Rate id.");
        }
    }

    @Override
    public Double getRateByStay(Long businessUnitId, Long rentalUnitId, LocalDate checkIn, LocalDate checkOut) {
        double totalPrice = 0.0;
        LocalDate currenDate = checkIn;
        while (!currenDate.isEqual(checkOut)){
            var rateDay = getRateForDay(businessUnitId,rentalUnitId,currenDate);
            if (rateDay!=null){
                totalPrice+=rateDay.getRate();
            }
            currenDate = currenDate.plusDays(1);
        }
        return totalPrice;
    }

    public RateEntity getRateForDay(Long businessUnitId, Long rentalUnitId, LocalDate date){
        var seasonEntity = iSeasonService.getSeasonEntityNotDeletedByBusinessUnitIdAndDate(businessUnitId, date);
        return iRateRepository.findByBusinessUnitAndRentalUnitAndDeletedAndSeasonId(businessUnitId, rentalUnitId, false, seasonEntity.getId());
    }
}
