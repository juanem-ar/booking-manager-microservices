package com.bookingmanager.hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    @GetMapping
    public ResponseEntity<String> testCallService(){
        return ResponseEntity.status(HttpStatus.OK).body("Hotel Service - UP");
    }
}
