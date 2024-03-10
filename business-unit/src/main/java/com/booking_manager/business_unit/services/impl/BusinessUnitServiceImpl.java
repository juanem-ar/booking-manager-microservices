package com.booking_manager.business_unit.services.impl;

import com.booking_manager.business_unit.mappers.IBusinessUnitMapper;
import com.booking_manager.business_unit.models.dtos.BusinessUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BusinessUnitResponseDto;
import com.booking_manager.business_unit.models.entities.BusinessUnitEntity;
import com.booking_manager.business_unit.models.entities.DeletedEntity;
import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
import com.booking_manager.business_unit.models.enums.EDeletedEntity;
import com.booking_manager.business_unit.models.enums.EStatus;
import com.booking_manager.business_unit.repositories.IBusinessUnitRepository;
import com.booking_manager.business_unit.repositories.IDeletedRepository;
import com.booking_manager.business_unit.repositories.IRentalUnitRepository;
import com.booking_manager.business_unit.services.IBusinessUnitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BusinessUnitServiceImpl implements IBusinessUnitService {
    private final IBusinessUnitRepository iBusinessUnitRepository;
    private final IRentalUnitRepository iRentalUnitRepository;
    private final IBusinessUnitMapper iBusinessUnitMapper;
    private final IDeletedRepository iDeletedRepository;

    @Override
    public BusinessUnitResponseDto saveBusinessUnit(BusinessUnitRequestDto dto) {
        var existsBusinessEntity = iBusinessUnitRepository.existsByNameAndDeleted(dto.getName(), false);
        if (existsBusinessEntity){
            throw new IllegalArgumentException("There is a business unit with that name.");
        }
        var entity = iBusinessUnitMapper.toEntity(dto);
        entity.setDeleted(false);
        entity.setRentalUnitList(new ArrayList<>());
        var entitySaved = iBusinessUnitRepository.save(entity);
        log.info("BusinessUnit added: {}", entitySaved);
        return iBusinessUnitMapper.toResponseDto(entitySaved);
    }
    @Override
    public BusinessUnitResponseDto updateBusinessUnit(BusinessUnitRequestDto dto, Long id) {
        var entity = getBusinessUnitById(id);
        if (!isDeleted(id)) {
            var entityMapped = iBusinessUnitMapper.updateEntity(dto, entity);
            var entitySaved = iBusinessUnitRepository.save(entityMapped);
            log.info("BusinessUnit Updated: {}", entitySaved);
            return iBusinessUnitMapper.toResponseDto(entitySaved);
        }else {
            throw new IllegalArgumentException("It's resource doesn't exists.");
        }
    }
    @Override
    public String deleteBusinessUnit(Long id) {
        var entity = getBusinessUnitById(id);
        if (!isDeleted(id)) {
            entity.setDeleted(true);
            var entitySaved = iBusinessUnitRepository.save(entity);
            var entityDeleted = DeletedEntity.builder()
                    .eDeletedEntity(EDeletedEntity.RENTAL_UNIT)
                    .idEntity(entity.getId())
                    .build();
            var registerDeleted = iDeletedRepository.save(entityDeleted);
            log.info("Business Unit has been deleted: {}", entitySaved);
            log.info("New Entity Save (DeleteEntity): {}", registerDeleted);
            List<RentalUnitEntity> rentalUnitList = iRentalUnitRepository.findAllByBusinessUnitIdAndDeleted(id,false);
            setDisabledStatus(rentalUnitList);
            log.info("Disabled Rental Units: {}", rentalUnitList.stream().toList());
            return "¡Business Unit has been deleted!";
        }else {
            throw new IllegalArgumentException("It's resource doesn't exists.");
        }
    }
    public void setDisabledStatus(List<RentalUnitEntity> rentalUnitList){
        for (RentalUnitEntity entity: rentalUnitList) {
            entity.setStatus(EStatus.STATUS_DISABLE);
            iRentalUnitRepository.save(entity);
        }
    }
    public BusinessUnitEntity getBusinessUnitById(Long id) {
        if (existsBusinessUnitById(id) && !isDeleted(id)){
            return iBusinessUnitRepository.getReferenceById(id);
        }else{
            throw new IllegalArgumentException("There isn´t a business unit with that id.");
        }
    }
    @Override
    public boolean existsBusinessUnitById(Long id){
        return iBusinessUnitRepository.existsById(id);
    }
    @Override
    public BusinessUnitResponseDto getBusinessUnitResponseDtoById(Long id) {
        var entity = getBusinessUnitById(id);
        return iBusinessUnitMapper.toResponseDto(entity);
    }
    public boolean isDeleted(Long id){
        var entity = iBusinessUnitRepository.getReferenceById(id);
        return entity.getDeleted();
    }
}
