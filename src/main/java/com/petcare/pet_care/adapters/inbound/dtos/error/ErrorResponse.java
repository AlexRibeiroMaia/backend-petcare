package com.petcare.pet_care.adapters.inbound.dtos.error;

public class ErrorResponse {
    private final int status;
    private final String messageError;

    public ErrorResponse(int status, String messageError) {
        this.status = status;
        this.messageError = messageError;
    }

    public int getStatus() {
        return status;
    }

    public String getMessageError() {
        return messageError;
    }
}
