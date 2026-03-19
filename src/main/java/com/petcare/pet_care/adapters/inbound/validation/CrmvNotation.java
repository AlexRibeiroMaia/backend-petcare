package com.petcare.pet_care.adapters.inbound.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CrmvValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CrmvNotation {

    String message() default "CRMV inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}