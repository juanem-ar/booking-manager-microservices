package com.booking_manager.booking.controllers;

import com.booking_manager.booking.models.dtos.BookingResponseDto;
import com.booking_manager.booking.models.dtos.BookingRequestDto;
import com.booking_manager.booking.models.dtos.BookingResponseDtoList;
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
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(iBookingService.getBooking(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDtoList>> getAllBooking(){
        return ResponseEntity.status(HttpStatus.OK).body(iBookingService.getAllBooking());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(iBookingService.deleteBooking(id));
    }
}
