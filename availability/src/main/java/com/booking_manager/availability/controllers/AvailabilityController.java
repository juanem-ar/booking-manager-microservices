package com.booking_manager.availability.controllers;

import com.booking_manager.availability.models.dtos.BaseResponse;
import com.booking_manager.availability.models.dtos.StayRequestDto;
import com.booking_manager.availability.models.dtos.StayResponseDto;
import com.booking_manager.availability.services.IStayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
@RequiredArgsConstructor
public class AvailabilityController {
    private final IStayService iStayService;

    @PostMapping
    public ResponseEntity<BaseResponse> createStay(@Validated @RequestBody StayRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iStayService.createStay(dto));
    }

    @GetMapping("/check")
    public ResponseEntity<BaseResponse> isThePeriodUnavailable(@Validated @RequestBody StayRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(iStayService.checkAvailabilityByBookingService(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<StayResponseDto>> getAllStaysByRentalUnitId(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iStayService.getAllStaysByRentalUnitId(id));
    }
    /* Codigo utilizado para corregir un error de persistencia doble
    @GetMapping("/booking/{id}")
    public ResponseEntity<List<StayResponseDto>> getAllStaysByBookingId(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iStayService.getAllStaysByBookingId(id));
    }
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteStayByRentalUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iStayService.deleteStay(id));
    }
    @DeleteMapping("/stay/{id}")
    public ResponseEntity<BaseResponse> deleteStay(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iStayService.deleteStayById(id));
    }
}
