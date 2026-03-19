package com.petcare.pet_care.adapters.inbound.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CrmvValidator implements ConstraintValidator<CrmvNotation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }

        // exemplo do crmv validado: 12345-SP
        return value.matches("^\\d{4,6}-[A-Z]{2}$");
    }
}