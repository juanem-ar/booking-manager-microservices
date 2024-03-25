package com.booking_manager.rate.controllers;

import com.booking_manager.rate.models.dtos.SeasonRequestDto;
import com.booking_manager.rate.models.dtos.SeasonResponseDto;
import com.booking_manager.rate.services.ISeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeasonController {
    private final ISeasonService iSeasonService;
    @PostMapping
    public ResponseEntity<SeasonResponseDto> createSeason(@Validated @RequestBody SeasonRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iSeasonService.createSeason(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SeasonResponseDto> getSeason(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iSeasonService.getSeason(id));
    }
    @GetMapping("/business-unit/{id}")
    public ResponseEntity<List<SeasonResponseDto>> getAllSeasonByBusinessId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iSeasonService.getAllSeasonByBusinessUnitId(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeason(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iSeasonService.deleteSeason(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<SeasonResponseDto> editSeason(@PathVariable Long id, @Validated @RequestBody SeasonRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(iSeasonService.editSeason(id, dto));
    }
}
