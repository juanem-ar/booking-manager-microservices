package com.booking_manager.rate.controllers;

import com.booking_manager.rate.models.dtos.TotalAmountComplexResponse;
import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;
import com.booking_manager.rate.services.IRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {
    private final IRateService iRateService;
    @PostMapping
    public ResponseEntity<RateResponseDto> createRate(@Validated @RequestBody RateRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iRateService.createRate(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RateResponseDto> getRate(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRateService.getRate(id));
    }
    @GetMapping("/business-unit/{businessUnitId}")
    public ResponseEntity<TotalAmountComplexResponse> getRateByBusinessUnitIdAndStay(@PathVariable Long businessUnitId,
                                                                                     @RequestParam(required = true) Long rentalUnitId,
                                                                                     @RequestParam(required = true) LocalDate checkIn,
                                                                                     @RequestParam(required = true) LocalDate checkOut){
        return ResponseEntity.status(HttpStatus.OK).body(iRateService.getRateByStay(businessUnitId,rentalUnitId, checkIn,checkOut));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<RateResponseDto> editRate(@PathVariable Long id, @Validated @RequestBody RateRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(iRateService.editRate(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRate(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRateService.deleteRate(id));
    }
}
