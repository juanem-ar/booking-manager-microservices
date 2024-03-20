package com.booking_manager.business_unit.controllers;

import com.booking_manager.business_unit.models.dtos.ServiceRequestDto;
import com.booking_manager.business_unit.models.dtos.ServiceResponseDto;
import com.booking_manager.business_unit.services.IServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServicesController{
    private final IServicesService iServicesService;

    @PostMapping
    public ResponseEntity<ServiceResponseDto> saveService(@Validated @RequestBody ServiceRequestDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iServicesService.saveService(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getService(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iServicesService.getService(id));
    }
    @GetMapping("/business-unit/{id}")
    public ResponseEntity<List<ServiceResponseDto>> getServicesByBusinessId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iServicesService.getServicesByBusinessId(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> editService(@PathVariable Long id, @RequestBody ServiceRequestDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iServicesService.editService(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iServicesService.deleteService(id));
    }
}
