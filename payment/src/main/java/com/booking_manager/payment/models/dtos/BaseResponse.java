package com.booking_manager.payment.models.dtos;

public record BaseResponse(String[] errorMessage) {
    public boolean hastErrors(){
        return errorMessage != null && errorMessage.length > 0;

    }
}
