package com.booking_manager.business_unit.services.impl;

import com.booking_manager.business_unit.mappers.IServiceMapper;
import com.booking_manager.business_unit.models.dtos.ServiceRequestDto;
import com.booking_manager.business_unit.models.dtos.ServiceResponseDto;
import com.booking_manager.business_unit.models.entities.DeletedEntity;
import com.booking_manager.business_unit.models.entities.ServicesEntity;
import com.booking_manager.business_unit.models.enums.EDeletedEntity;
import com.booking_manager.business_unit.repositories.IBusinessUnitRepository;
import com.booking_manager.business_unit.repositories.IDeletedRepository;
import com.booking_manager.business_unit.repositories.IServiceRepository;
import com.booking_manager.business_unit.services.IServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements IServicesService {
    private final IServiceRepository iServiceRepository;
    private final IServiceMapper iServiceMapper;
    private final IBusinessUnitRepository iBusinessUnitRepository;
    private final IDeletedRepository iDeletedRepository;

    @Override
    public ServiceResponseDto saveService(ServiceRequestDto dto) throws BadRequestException {
        var entity = getServiceEntityIfExistsByTitle(dto.getTitle());
        if (entity == null){
            var mappedEntity = iServiceMapper.toEntity(dto);
            mappedEntity.setDeleted(Boolean.FALSE);
            var savedEntity = iServiceRepository.save(mappedEntity);
            log.info("Service Saved: {}", savedEntity);
            return iServiceMapper.toResponseDto(savedEntity);
        }else{
            throw new BadRequestException("Service is already exists.");
        }
    }

    @Override
    public ServiceResponseDto getService(Long id) throws BadRequestException {
        var entity = getServiceEntityIfExistsById(id);
        return iServiceMapper.toResponseDto(entity);
    }

    private ServicesEntity getServiceEntityIfExistsById(Long id) throws BadRequestException {
        var entity = iServiceRepository.getReferenceByIdAndDeleted(id, false);
        if (entity != null) {
            return entity;
        }else {
            throw new BadRequestException("Invalid service id");
        }
    }
    private ServicesEntity getServiceEntityIfExistsByTitle(String title){
        return iServiceRepository.getReferenceByTitleAndDeleted(title, false);
    }

    @Override
    public List<ServiceResponseDto> getServicesByBusinessId(Long businessUnitId) {
        iBusinessUnitRepository.existsByIdAndDeleted(businessUnitId, false);
        List<ServicesEntity> entityList = iServiceRepository.findAllByBusinessUnitIdAndDeletedOrderById(businessUnitId, false);
        if (!entityList.isEmpty())
            return iServiceMapper.toServicesResponseDtoList(entityList);
        else
            return new ArrayList<>();
    }

    @Override
    public ServiceResponseDto editService(Long id, ServiceRequestDto dto) throws BadRequestException {
        var entity = getServiceEntityIfExistsById(id);
        var existsTitle = iServiceRepository.existsByTitleAndDeleted(dto.getTitle(), false);
        if (!existsTitle){
            var mappedEntity = iServiceMapper.updateEntity(dto, entity);
            var savedEntity = iServiceRepository.save(mappedEntity);
            log.info("Service Updated: {}", mappedEntity);
            return iServiceMapper.toResponseDto(savedEntity);
        }else{
            throw new BadRequestException("Invalid title.");
        }

    }

    @Override
    public String deleteService(Long id) throws BadRequestException {
        var entity = getServiceEntityIfExistsById(id);
        entity.setDeleted(Boolean.TRUE);
        var savedEntity = iServiceRepository.save(entity);
        var deleteEntity = DeletedEntity.builder()
                .eDeletedEntity(EDeletedEntity.SERVICE)
                .idEntity(entity.getId())
                .build();
        var savedDeleteEntity = iDeletedRepository.save(deleteEntity);
        log.info("Service has been deleted: {}", savedEntity.getTitle());
        log.info("New Entity Save (DeleteEntity): {}", savedDeleteEntity);
        return "¡Service has been deleted!";
    }
}
