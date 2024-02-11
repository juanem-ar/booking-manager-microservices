package com.booking_manager.business_unit.controllers;

import com.booking_manager.business_unit.models.dtos.BusinessUnitRequestDto;
import com.booking_manager.business_unit.models.dtos.BusinessUnitResponseDto;
import com.booking_manager.business_unit.services.IBusinessUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business-units")
@RequiredArgsConstructor
public class BusinessUnitController {

    private final IBusinessUnitService iBusinessUnitService;

    @PostMapping
    public ResponseEntity<BusinessUnitResponseDto> saveBusinessUnit(@RequestBody BusinessUnitRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iBusinessUnitService.saveBusinessUnit(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessUnitResponseDto> getBusinessUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iBusinessUnitService.getBusinessUnitResponseDtoById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BusinessUnitResponseDto> editBusinessUnit(@RequestBody BusinessUnitRequestDto dto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iBusinessUnitService.updateBusinessUnit(dto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBusinessUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iBusinessUnitService.deleteBusinessUnit(id));
    }
}
