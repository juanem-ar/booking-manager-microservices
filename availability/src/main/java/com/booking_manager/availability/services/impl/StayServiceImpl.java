package com.booking_manager.availability.services.impl;

import com.booking_manager.availability.mappers.IStayMapper;
import com.booking_manager.availability.models.dtos.*;
import com.booking_manager.availability.models.entities.DeletedEntity;
import com.booking_manager.availability.models.entities.StayEntity;
import com.booking_manager.availability.repositories.IDeletedRepository;
import com.booking_manager.availability.repositories.IStayRepository;
import com.booking_manager.availability.services.IStayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StayServiceImpl implements IStayService {
    private final IStayRepository iStayRepository;
    private final IStayMapper iStayMapper;
    private final IDeletedRepository iDeletedRepository;

    @Override
    public BaseResponse createStay(StayRequestDto dto) {
        var errorList = new ArrayList<String>();
        if (dto.getCheckIn().equals(dto.getCheckOut()))
            errorList.add("check in and check out ares equals");
        if (!checkAvailability(dto)){
            var entity = iStayMapper.toStayEntity(dto);
            entity.setDeleted(Boolean.FALSE);
            var entitySaved = iStayRepository.save(entity);
            log.info("Stay Saved: {}", entitySaved);
        }else{
            errorList.add("Error when trying to validate the availability of period. Rental unit is not available.");
        }
        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
    @Override
    public List<StayResponseDto> getAllStaysByRentalUnitId(Long id) throws Exception {
        var staysList = iStayRepository.findAllByDeletedAndRentalUnitId(false, id);
        if(!staysList.isEmpty())
            return iStayMapper.toStayResponseDtoList(staysList);
        else
            return new ArrayList<StayResponseDto>();
    }
    @Override
    public StayComplexResponseByGet getStayByBookingId(Long id){
        List<String> errorList = new ArrayList<>();
        StayComplexResponseByGet result;

        var stay = iStayRepository.findByBookingIdAndDeleted(id, false);

        if(stay != null){
            var mappedEntity = iStayMapper.toSimpleStayResponseDto(stay);
            result = StayComplexResponseByGet.builder().stay(mappedEntity).baseResponse(new BaseResponse(null)).build();
        }else{
            errorList.add("Invalid booking id.");
            result = StayComplexResponseByGet.builder().stay(null).baseResponse(new BaseResponse(errorList.toArray(new String[0]))).build();
        }
        return result;
    }
    @Override
    public BaseResponse deleteStay(Long id) {
        var errorList = new ArrayList<String>();
        var entity = iStayRepository.findByBookingIdAndDeleted(id, false);
        if (entity!=null){
            setDeletedAndSaveEntity(entity);
        }else{
            errorList.add("Invalid Booking Id.");
        }
        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }

    public void setDeletedAndSaveEntity(StayEntity entity){
        entity.setDeleted(Boolean.TRUE);
        var entitySaved = iStayRepository.save(entity);
        var entityDeleted = DeletedEntity.builder()
                .stayId(entitySaved.getId())
                .bookingId(entitySaved.getBookingId())
                .build();
        var savedEntityDeleted = iDeletedRepository.save(entityDeleted);
        log.info("Stay has been deleted: {}", entitySaved);
        log.info("New Entity Save (DeleteEntity): {}", savedEntityDeleted);
    }

    @Override
    public BaseResponse deleteStayById(Long id) {
        var errorList = new ArrayList<String>();
        var entity = iStayRepository.findByIdAndDeleted(id, false);
        if (entity!=null){
            setDeletedAndSaveEntity(entity);
        }else {
            errorList.add("Invalid Id.");
        }
        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }

    public BaseResponse checkAvailabilityByBookingService(StayRequestDto dto){
        var errorList = new ArrayList<String>();
        var isUnavailable = checkAvailability(dto);
        if (isUnavailable)
            errorList.add("Unavailable Stay");
        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
    public Boolean checkAvailability(StayRequestDto dto){
        boolean result = iStayRepository.existsByCheckInLessThanAndCheckOutGreaterThanAndDeletedAndRentalUnitId(dto.getCheckOut(), dto.getCheckIn(), false, dto.getRentalUnitId());
        log.info("Stay find RESULT: {}", result);
        return result;
    }
}
