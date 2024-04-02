package com.booking_manager.rate.services.impl;

import com.booking_manager.rate.mappers.IRateMapper;
import com.booking_manager.rate.models.dtos.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        log.info("new Rate: {}", savedEntity);
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
    public TotalAmountComplexResponse getRateByStay(Long businessUnitId, Long rentalUnitId, LocalDate checkIn, LocalDate checkOut) {
        List<String> errorList = new ArrayList<>();
        double totalPrice = 0.0;
        LocalDate currenDate = checkIn;

        while (!currenDate.isEqual(checkOut)){
            RateComplexResponse rateDay = getRateForDay(businessUnitId,rentalUnitId,currenDate);
            if (rateDay != null && !rateDay.getBaseResponse().hastErrors()){
                totalPrice+=rateDay.getRate().getRate();
            }else{
                errorList.add("Error to get rates. " + Arrays.toString(rateDay.getBaseResponse().errorMessage()));
            }
            currenDate = currenDate.plusDays(1);
        }
        var resultWithErrors = TotalAmountComplexResponse.builder().baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();
        var resultWithoutErrors = TotalAmountComplexResponse.builder().totalAmount(totalPrice).baseResponse(new BaseResponse(null)).build();
        return errorList.size() > 0 ?  resultWithErrors : resultWithoutErrors;
    }

    public RateComplexResponse getRateForDay(Long businessUnitId, Long rentalUnitId, LocalDate date){
        SeasonComplexResponse seasonEntityResponse = iSeasonService.getSeasonEntityNotDeletedByBusinessUnitIdAndDate(businessUnitId, date);
        if (seasonEntityResponse != null && !seasonEntityResponse.getBaseResponse().hastErrors()){
            var rateEntity = iRateRepository.findByBusinessUnitAndRentalUnitAndDeletedAndSeasonId(businessUnitId, rentalUnitId, false, seasonEntityResponse.getSeason().getId());
            if (rateEntity != null){
                return RateComplexResponse.builder().rate(rateEntity).baseResponse(new BaseResponse(null)).build();
            }else{
                return RateComplexResponse.builder().baseResponse(new BaseResponse(new String[]{"Error to get prices of seasons"})).build();
            }
        }else{
            return RateComplexResponse.builder().baseResponse(seasonEntityResponse.getBaseResponse()).build();
        }
    }

    @Override
    public RateResponseDto editRate(Long id, RateRequestDto dto) {
        var entity = getRateEntityNotDeletedById(id);
        var mappedEntity = iRateMapper.updateEntity(dto, entity);
        var savedEntity = iRateRepository.save(mappedEntity);
        log.info("Rate has been edited: {}", savedEntity);
        return iRateMapper.toResponseDto(savedEntity);
    }
}