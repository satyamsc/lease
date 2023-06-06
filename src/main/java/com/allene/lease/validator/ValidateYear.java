package com.allene.lease.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = VehicleModelYearValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateYear {

    String message() default "Model year is Invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
