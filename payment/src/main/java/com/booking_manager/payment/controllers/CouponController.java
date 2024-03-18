package com.booking_manager.payment.controllers;

import com.booking_manager.payment.models.dtos.CouponRequestDto;
import com.booking_manager.payment.models.dtos.CouponResponseDto;
import com.booking_manager.payment.models.dtos.CouponResponseDtoWithEntity;
import com.booking_manager.payment.services.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final ICouponService iCouponService;

    @PostMapping
    public ResponseEntity<CouponResponseDto> createCoupon(@Validated @RequestBody CouponRequestDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iCouponService.createCoupon(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDto> getCouponById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iCouponService.getCouponById(id));
    }
    @GetMapping
    public ResponseEntity<List<CouponResponseDto>> getAllCoupons(){
        return ResponseEntity.status(HttpStatus.OK).body(iCouponService.getAllCoupons());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CouponResponseDto> editCoupon(@Validated @RequestBody CouponRequestDto dto, @PathVariable  Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iCouponService.editCoupon(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@Validated @PathVariable  Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iCouponService.deleteCoupon(id));
    }

    @PutMapping("/{totalAmount}/{bookingCheckIn}/{bookingCheckOut}/{duration}/{code}")
    public CouponResponseDtoWithEntity applyCoupon(@PathVariable Double totalAmount,
                                                   @PathVariable LocalDate bookingCheckIn,
                                                   @PathVariable LocalDate bookingCheckOut,
                                                   @PathVariable Long duration,
                                                   @PathVariable String code) throws Exception {
        return iCouponService.applyDiscount(totalAmount, bookingCheckIn, bookingCheckOut, duration, code);
    }

}
