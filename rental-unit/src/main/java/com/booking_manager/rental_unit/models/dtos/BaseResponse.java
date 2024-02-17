package com.booking_manager.rental_unit.models.dtos;

public record BaseResponse(String[] errorMessage) {
    public boolean hasErrors(){
        return errorMessage != null && errorMessage.length > 0;
    }
}
