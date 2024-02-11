package com.booking_manager.business_unit.services.impl;

import com.booking_manager.business_unit.mappers.IBusinessUnitMapper;
import com.booking_manager.business_unit.models.dtos.BusinessUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BusinessUnitResponseDto;
import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import com.booking_manager.business_unit.repositories.IBusinessUnitRepository;
import com.booking_manager.business_unit.services.IBusinessUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessUnitServiceImpl implements IBusinessUnitService {
    private final IBusinessUnitRepository iBusinessUnitRepository;
    private final IBusinessUnitMapper iBusinessUnitMapper;

    @Override
    public BusinessUnitResponseDto saveBusinessUnit(BusinessUnitRequestDto dto) {
        var existsBusinessEntity = iBusinessUnitRepository.existsByName(dto.getName());
        if (existsBusinessEntity){
            throw new IllegalArgumentException("There is a business unit with that name.");
        }
        var entity = iBusinessUnitMapper.toEntity(dto);
        entity.setDeleted(false);
        var entitySaved = iBusinessUnitRepository.save(entity);
        log.info("BusinessUnit added: {}", entitySaved);
        return iBusinessUnitMapper.toResponseDto(entitySaved);
    }
    @Override
    public BusinessUnitResponseDto updateBusinessUnit(BusinessUnitRequestDto dto, Long id) {
        var entity = getBusinessUnitById(id);
        var entityMapped = iBusinessUnitMapper.updateEntity(dto, entity);
        var entitySaved = iBusinessUnitRepository.save(entityMapped);
        log.info("BusinessUnit Updated: {}", entitySaved);
        return iBusinessUnitMapper.toResponseDto(entitySaved);
    }
    @Override
    public String deleteBusinessUnit(Long id) {
        var entity = getBusinessUnitById(id);
        entity.setDeleted(true);
        iBusinessUnitRepository.save(entity);
        log.info("BusinessUnit Deleted: {}", entity);
        return "¡Business Unit Deleted!";
    }
    public BusinessUnitEntity getBusinessUnitById(Long id) {
        if (!iBusinessUnitRepository.existsById(id)){
            throw new IllegalArgumentException("There isn´t a business unit with that id.");
        }
        return iBusinessUnitRepository.getReferenceById(id);
    }
    @Override
    public BusinessUnitResponseDto getBusinessUnitResponseDtoById(Long id) {
        var entity = getBusinessUnitById(id);
        return iBusinessUnitMapper.toResponseDto(entity);
    }
}
