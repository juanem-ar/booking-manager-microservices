package com.booking_manager.booking.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTotalAmountDto {
    private Double totalAmount;
    private List<ServicesPriceDto> services;
    private List<Long> serviceIdList;
    private List<String> hastErrors;
}
