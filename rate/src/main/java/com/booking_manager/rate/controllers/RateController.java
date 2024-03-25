package com.booking_manager.rate.controllers;

import com.booking_manager.rate.models.dtos.RateRequestDto;
import com.booking_manager.rate.models.dtos.RateResponseDto;
import com.booking_manager.rate.services.IRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {
    private final IRateService iRateService;
    @PostMapping
    public ResponseEntity<RateResponseDto> createRate(@RequestBody RateRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iRateService.createRate(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RateResponseDto> getRate(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRateService.getRate(id));
    }
}
