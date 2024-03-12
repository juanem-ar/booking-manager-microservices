package com.booking_manager.payment.controllers;

import com.booking_manager.payment.models.dtos.*;
import com.booking_manager.payment.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService iPaymentService;
    @PostMapping
    public ResponseEntity<ComplexResponse> createPayment(@Validated @RequestBody PaymentRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPaymentService.createPayment(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deletePayment(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPaymentService.deletePayment(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<PaymentResponseDto>> getAllPaymentsByBookingId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.getAllPaymentsByBookingId(id));
    }
    @PostMapping("/new-payment")
    public ResponseEntity<PaymentResponseDto> newPayment(@Validated @RequestBody NewPaymentRequestDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.savePayment(dto));
    }
}
