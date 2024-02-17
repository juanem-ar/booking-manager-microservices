package com.booking_manager.rental_unit.controllers;

import com.booking_manager.rental_unit.models.dtos.RentalUnitRequestDto;
import com.booking_manager.rental_unit.models.dtos.RentalUnitResponseDto;
import com.booking_manager.rental_unit.services.IRentalUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental-units")
@RequiredArgsConstructor
public class RentalUnitController {
    private final IRentalUnitService iRentalUnitService;

    @PostMapping
    public ResponseEntity<RentalUnitResponseDto> saveRentalUnit(@RequestBody RentalUnitRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iRentalUnitService.saveRentalUnit(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalUnitResponseDto> getRentalUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getRentalUnitResponseDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<RentalUnitResponseDto>> getAllRentalUnitByBusinessUnitId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getAllRentalUnitResponseDtoByBusinessUnitId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RentalUnitResponseDto> editRentalUnit(@RequestBody RentalUnitRequestDto dto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.updateRentalUnit(dto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRentalUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.deleteRentalUnit(id));
    }
}
