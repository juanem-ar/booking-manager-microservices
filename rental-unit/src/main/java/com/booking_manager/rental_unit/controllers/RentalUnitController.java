package com.booking_manager.rental_unit.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rental-units")
public class RentalUnitController {
    @GetMapping
    public ResponseEntity<String> testCallService(){
        return ResponseEntity.status(HttpStatus.OK).body("Rental Unit Service - UP");
    }
}
