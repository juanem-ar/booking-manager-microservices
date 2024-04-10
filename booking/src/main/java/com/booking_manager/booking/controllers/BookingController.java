package com.booking_manager.booking.controllers;

import com.booking_manager.booking.models.dtos.*;
import com.booking_manager.booking.services.IBookingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final IBookingService iBookingService;
    @PostMapping
    @CircuitBreaker(name = "booking-service", fallbackMethod = "createBookingFallback")
    public ResponseEntity<BookingResponseDto> createBooking(@Validated @RequestBody BookingRequestDto dto) throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED).body(iBookingService.createBooking(dto));
    }
    public ResponseEntity<String> createBookingFallback(Throwable throwable){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(throwable.getMessage());
    }
    @PostMapping("/{id}")
    public ResponseEntity<BookingFullResponseDto> addGuestToBookingByBookingId(@Validated @RequestBody GuestRequestDto dto, @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iBookingService.addGuestToBookingByBookingId(dto,id));
    }
    @GetMapping("/{id}")
    @CircuitBreaker(name = "booking-service", fallbackMethod = "createBookingFallback")
    public ResponseEntity<BookingFullResponseDto> getBooking(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(iBookingService.getBooking(id));
    }
    @GetMapping("/rental-unit/{id}")
    public ResponseEntity<List<BookingResponseDtoList>> getAllBookingByRentalUnit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iBookingService.getAllBookingByRentalUnit(id));
    }
    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "booking-service", fallbackMethod = "createBookingFallback")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(iBookingService.deleteBooking(id));
    }
}
