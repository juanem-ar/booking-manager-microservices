package com.booking_manager.booking.services.impl;

import com.booking_manager.booking.mappers.IGuestMapper;
import com.booking_manager.booking.models.dtos.GuestRequestDto;
import com.booking_manager.booking.models.dtos.GuestResponseDto;
import com.booking_manager.booking.models.entities.BookingEntity;
import com.booking_manager.booking.models.entities.GuestEntity;
import com.booking_manager.booking.repositories.IGuestRepository;
import com.booking_manager.booking.services.IGuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements IGuestService {
    private final IGuestRepository iGuestRepository;
    private final IGuestMapper iGuestMapper;


    @Override
    public GuestResponseDto saveGuest(GuestRequestDto dto) {
        var savedEntity = addGuest(dto);
        return iGuestMapper.toResponseDto(savedEntity);
    }

    @Override
    public GuestEntity addGuest(GuestRequestDto dto) {
        var entity = iGuestMapper.toEntity(dto);
        entity.setDeleted(Boolean.FALSE);
        entity.setBooking(new ArrayList<>());
        var savedEntity = iGuestRepository.save(entity);
        log.info("Guest saved: {}", savedEntity);
        return savedEntity;
    }

    @Override
    public GuestResponseDto getGuestByEmail(String email) {
        var entity = iGuestRepository.getReferenceByEmailAndDeleted(email, false);
        return iGuestMapper.toResponseDto(entity);
    }

    @Override
    public GuestResponseDto addToBookingList(Long guestId, BookingEntity booking) {
        var entity = iGuestRepository.getReferenceByIdAndDeleted(guestId, false);
        var bookingList = entity.getBooking();
        bookingList.add(booking);
        var savedEntity = iGuestRepository.save(entity);
        return iGuestMapper.toResponseDto(savedEntity);
    }
}
