package com.booking_manager.rental_unit.services.impl;

import com.booking_manager.rental_unit.models.dtos.BaseResponse;
import com.booking_manager.rental_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.rental_unit.models.dtos.RentalUnitResponseDto;
import com.booking_manager.rental_unit.models.entities.RentalUnitEntity;
import com.booking_manager.rental_unit.models.enums.EStatus;
import com.booking_manager.rental_unit.repositories.iRentalUnitRepository;
import com.booking_manager.rental_unit.services.IRentalUnitService;
import com.booking_manager.rental_unit.mappers.iRentalUnitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalUnitServiceImpl implements IRentalUnitService {

    private final iRentalUnitRepository iRentalUnitRepository;
    private final iRentalUnitMapper iRentalUnitMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public RentalUnitResponseDto saveRentalUnit(RentalUnitRequestDto dto) {
        //TODO VALIDAR EXISTENCIA DE BUSINESS UNIT
        BaseResponse result = this.webClientBuilder.build()
                .get()
                .uri("lb://business-unit-service/api/business-units/exists/" + dto.getBusinessUnit())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
        //TODO BORRAR LOG
        if (result != null && !result.hasErrors()) {
            RentalUnitEntity entity = iRentalUnitMapper.toRentalUnit(dto);
            entity.setDeleted(Boolean.FALSE);
            entity.setStatus(EStatus.STATUS_ENABLE);
            var entitySaved = iRentalUnitRepository.save(entity);
            return iRentalUnitMapper.toRentalUnitResponseDto(entitySaved);
        } else {
            throw new IllegalArgumentException("There isn´t a business unit with that id.");
        }
    }

    @Override
    public RentalUnitResponseDto getRentalUnitResponseDtoById(Long id) {
        return null;
    }

    @Override
    public List<RentalUnitResponseDto> getAllRentalUnitResponseDtoByBusinessUnitId(Long id) {
        var entityList = this.iRentalUnitRepository.findAllByBusinessUnit(id);
        return this.iRentalUnitMapper.toRentalUnitResponseDtoList(entityList);
    }

    @Override
    public RentalUnitResponseDto updateRentalUnit(RentalUnitRequestDto dto, Long id) {
        return null;
    }

    @Override
    public String deleteRentalUnit(Long id) {
        return null;
    }
}
