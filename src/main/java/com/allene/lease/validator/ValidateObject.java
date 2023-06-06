package com.allene.lease.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ObjectTypeValidator.class)
public @interface ValidateObject {

    String message() default "Id is mandatory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}