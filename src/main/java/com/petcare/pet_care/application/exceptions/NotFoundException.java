package com.petcare.pet_care.application.exceptions;

public class NotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "ID not found, verify and try again";

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
