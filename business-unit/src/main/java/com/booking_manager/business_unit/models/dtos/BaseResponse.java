package com.booking_manager.business_unit.models.dtos;

public record BaseResponse(String[] errorMessage) {
    public boolean hastErrors(){
        return errorMessage != null && errorMessage.length > 0;
    }
}
