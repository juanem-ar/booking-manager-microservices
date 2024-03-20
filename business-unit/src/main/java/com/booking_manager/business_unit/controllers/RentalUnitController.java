package com.booking_manager.business_unit.controllers;

import com.booking_manager.business_unit.models.dtos.AvailabilityRentalUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BaseResponse;
import com.booking_manager.business_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.RentalUnitResponseDto;
import com.booking_manager.business_unit.services.IRentalUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental-units")
@RequiredArgsConstructor
public class RentalUnitController {
    private final IRentalUnitService iRentalUnitService;

    @PostMapping
    public ResponseEntity<RentalUnitResponseDto> saveRentalUnit(@Validated @RequestBody RentalUnitRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iRentalUnitService.saveRentalUnit(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RentalUnitResponseDto> getRentalUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getRentalUnitResponseDtoById(id));
    }
    @GetMapping("/business-unit/{id}")
    public ResponseEntity<List<RentalUnitResponseDto>> getAllRentalUnitByBusinessUnitId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getAllRentalUnitResponseDtoByBusinessUnitId(id));
    }
    @PostMapping("/available")
    public ResponseEntity<BaseResponse> existsRentalUnitByAvailableRequestDto(@RequestBody AvailabilityRentalUnitRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.existsRentalUnitByAvailableRequestDto(dto));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<RentalUnitResponseDto> editRentalUnit(@Validated @RequestBody RentalUnitRequestDto dto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(iRentalUnitService.updateRentalUnit(dto, id));
    }
    @PatchMapping("/{id}/change-status")
    public ResponseEntity<String> disableRentalUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(iRentalUnitService.changeStatusRentalUnit(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRentalUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(iRentalUnitService.deleteRentalUnit(id));
    }
}
