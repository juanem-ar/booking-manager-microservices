package com.booking_manager.payment.controllers;

import com.booking_manager.payment.models.dtos.*;
import com.booking_manager.payment.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService iPaymentService;
    @PostMapping("/booking/{id}")
    public ResponseEntity<ComplexResponseBySave> createPayment(@Validated @RequestBody BookingRequestDto dto, @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iPaymentService.createPayment(dto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deletePayment(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPaymentService.deletePayment(id));
    }
    @GetMapping("/booking/{id}")
    public ResponseEntity<PaymentComplexResponseByGet> getAllPaymentsByBookingId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.getAllPaymentsByBookingId(id));
    }
    @PostMapping("/new")
    public ResponseEntity<PaymentResponseDto> newPayment(@Validated @RequestBody NewPaymentRequestDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.savePayment(dto));
    }
}
