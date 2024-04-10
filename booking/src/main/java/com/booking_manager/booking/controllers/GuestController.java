package com.booking_manager.booking.controllers;

import com.booking_manager.booking.models.dtos.GuestRequestDto;
import com.booking_manager.booking.models.dtos.GuestResponseDto;
import com.booking_manager.booking.services.IGuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {
    private final IGuestService iGuestService;

    @PostMapping
    public ResponseEntity<GuestResponseDto> addGuest(@Validated @RequestBody GuestRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iGuestService.saveGuest(dto));
    }
    @GetMapping
    public ResponseEntity<GuestResponseDto> getGuest(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(iGuestService.getGuestByEmail(email));
    }
}
