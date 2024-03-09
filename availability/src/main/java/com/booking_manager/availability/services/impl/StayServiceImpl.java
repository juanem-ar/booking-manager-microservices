package com.booking_manager.availability.services.impl;

import com.booking_manager.availability.mappers.IStayMapper;
import com.booking_manager.availability.models.dtos.BaseResponse;
import com.booking_manager.availability.models.dtos.StayResponseDto;
import com.booking_manager.availability.models.dtos.StayRequestDto;
import com.booking_manager.availability.models.entities.DeletedEntity;
import com.booking_manager.availability.repositories.IDeletedRepository;
import com.booking_manager.availability.repositories.IStayRepository;
import com.booking_manager.availability.services.IStayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StayServiceImpl implements IStayService {
    private final IStayRepository iStayRepository;
    private final IStayMapper iStayMapper;
    private final IDeletedRepository iDeletedRepository;

    @Override
    public BaseResponse createStay(StayRequestDto dto) throws Exception {
        validateStay(dto.getCheckIn(), dto.getCheckOut());
        var errorList = new ArrayList<String>();
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
    public List<StayResponseDto> getAllStays(Long id) throws Exception {
        var staysList = iStayRepository.findAllByDeletedAndRentalUnitId(false, id);
        if(staysList.size() > 0)
            return iStayMapper.toStayResponseDtoList(staysList);
        else
            //TODO crear manejador de errores y exception para recursos vacios con status 200
            throw new BadRequestException("The Rental Unit has no stays");
    }
    @Override
    public BaseResponse deleteStay(Long id) {
        var errorList = new ArrayList<String>();
        var entity = iStayRepository.findByBookingIdAndDeleted(id, false);
        if (entity!=null){
            entity.setDeleted(Boolean.TRUE);
            var entitySaved = iStayRepository.save(entity);
            var entityDeleted = DeletedEntity.builder()
                    .stayId(entitySaved.getId())
                    .bookingId(entitySaved.getBookingId())
                    .build();
            var savedEntityDeleted = iDeletedRepository.save(entityDeleted);
            log.info("Stay has been deleted: {}", entitySaved);
            log.info("New Entity Save (DeleteEntity): {}", savedEntityDeleted);
        }else{
            errorList.add("Invalid Stay Id.");
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

    public void validateStay(LocalDate checkIn, LocalDate checkOut) throws BadRequestException {
        if (checkIn.equals(checkOut))
            throw new BadRequestException("check in and check out ares equals");
        if (checkIn.isAfter(checkOut))
            throw new BadRequestException("Invalid date");
    }
}
