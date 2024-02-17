package com.booking_manager.business_unit.services.impl;

import com.booking_manager.business_unit.mappers.IRentalUnitMapper;
import com.booking_manager.business_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.RentalUnitResponseDto;
import com.booking_manager.business_unit.models.entities.DeletedEntity;
import com.booking_manager.business_unit.models.entities.RentalUnitEntity;
import com.booking_manager.business_unit.models.enums.EDeletedEntity;
import com.booking_manager.business_unit.models.enums.EStatus;
import com.booking_manager.business_unit.repositories.IBusinessUnitRepository;
import com.booking_manager.business_unit.repositories.IDeletedRepository;
import com.booking_manager.business_unit.repositories.IRentalUnitRepository;
import com.booking_manager.business_unit.services.IRentalUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalUnitServiceImpl implements IRentalUnitService {
    private final IBusinessUnitRepository iBusinessUnitRepository;
    private final IRentalUnitMapper iRentalUnitMapper;
    private final IRentalUnitRepository iRentalUnitRepository;
    private final IDeletedRepository iDeletedRepository;

    @Override
    public RentalUnitResponseDto saveRentalUnit(RentalUnitRequestDto dto) {
        if (exists(dto)){
            var entity = iRentalUnitMapper.toRentalUnit(dto);
            entity.setDeleted(Boolean.FALSE);
            entity.setStatus(EStatus.STATUS_ENABLE);
            var entitySaved= iRentalUnitRepository.save(entity);
            log.info("Rental Unit Saved: {}", entitySaved);
            return iRentalUnitMapper.toRentalUnitResponseDto(entitySaved);
        }else{
            throw new IllegalArgumentException("Invalid Business Unit Id");
        }
    }
    @Override
    public RentalUnitResponseDto getRentalUnitResponseDtoById(Long id) {
        if (exists(id)) {
            if (!isDeleted(id)) {
                var entity = iRentalUnitRepository.getReferenceById(id);
                return iRentalUnitMapper.toRentalUnitResponseDto(entity);
            }else{
                throw new IllegalArgumentException("It's resource doesn't exists.");
            }
        }else{
            throw new IllegalArgumentException("Invalid Rental Unit Id");
        }
    }
    @Override
    public List<RentalUnitResponseDto> getAllRentalUnitResponseDtoByBusinessUnitId(Long id) {
        var entity = iBusinessUnitRepository.existsById(id);
        if (entity){
            List<RentalUnitEntity> entityList = iRentalUnitRepository.findAllByBusinessUnitIdAndDeleted(id, false);
            return iRentalUnitMapper.toRentalUnitResponseDtoList(entityList);
        }else{
            throw new IllegalArgumentException("Invalid Business Unit Id");
        }
    }
    @Override
    public RentalUnitResponseDto updateRentalUnit(RentalUnitRequestDto dto, Long id) {
        if (exists(id)){
            if (!isDeleted(id)){
                var entity = iRentalUnitRepository.getReferenceById(id);
                var entityMapped = iRentalUnitMapper.updateEntity(dto, entity);
                var entitySaved = iRentalUnitRepository.save(entityMapped);
                log.info("Rental Unit Updated: {}", entitySaved);
                return iRentalUnitMapper.toRentalUnitResponseDto(entitySaved);
            }else{
                throw new IllegalArgumentException("It's resource doesn't exists.");
            }
        }else {
            throw new IllegalArgumentException("Invalid Rental Unit Id");
        }
    }
    @Override
    public String deleteRentalUnit(Long id) {
        if (exists(id)) {
            if (!isDeleted(id)) {
                var entity = iRentalUnitRepository.getReferenceById(id);
                entity.setDeleted(Boolean.TRUE);
                var entitySaved = iRentalUnitRepository.save(entity);
                var entityDeleted = DeletedEntity.builder()
                        .eDeletedEntity(EDeletedEntity.RENTAL_UNIT)
                        .idEntity(entity.getId())
                        .build();
                var registerDeleted = iDeletedRepository.save(entityDeleted);
                log.info("Rental Unit Deleted: {}", entitySaved);
                log.info("New Entity Deleted: {}", registerDeleted);
                return "¡Rental Unit Deleted!";
            }else{
                throw new IllegalArgumentException("It's resource doesn't exists.");
            }
        }else{
            throw new IllegalArgumentException("Invalid Rental Unit Id");
        }
    }
    public String changeStatusRentalUnit(Long id){
        var entity = iRentalUnitRepository.getReferenceById(id);
        if (exists(id)){
            if (!isDeleted(id)) {
                if (entity.getStatus().equals(EStatus.STATUS_ENABLE))
                    entity.setStatus(EStatus.STATUS_DISABLE);
                else
                    entity.setStatus(EStatus.STATUS_ENABLE);
                iRentalUnitRepository.save(entity);
                log.info("Status changed: {}", entity.getStatus());
                return "Status changed.";
            }else{
                throw new IllegalArgumentException("It's resource doesn't exists.");
            }
        }else{
            throw new IllegalArgumentException("Invalid Rental Unit Id");
        }
    }

    public boolean exists(Object object){
        if (object instanceof RentalUnitRequestDto){
            return iBusinessUnitRepository.existsById(((RentalUnitRequestDto) object).getBusinessUnit());
        }else if (object instanceof Long){
            return iRentalUnitRepository.existsById((Long) object);
        }else{
            throw new IllegalArgumentException("Invalid parameter.");
        }
    }
    public boolean isDeleted(Long id){
        var entity = iRentalUnitRepository.getReferenceById(id);
        return entity.getDeleted();
    }
}
