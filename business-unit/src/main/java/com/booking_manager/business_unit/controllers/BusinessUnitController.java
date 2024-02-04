package com.booking_manager.business_unit.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/business-units")
public class BusinessUnitController {
    @GetMapping
    public ResponseEntity<String> testCallService(){
        return ResponseEntity.status(HttpStatus.OK).body("Business Unit Service - UP");
    }
}
