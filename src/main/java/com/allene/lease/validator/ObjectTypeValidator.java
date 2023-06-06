package com.allene.lease.validator;

import com.allene.lease.dto.AbstractDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Custom validator for validating an object type.
 */
public class ObjectTypeValidator implements ConstraintValidator<ValidateObject, AbstractDTO<?>> {
    /**
     * Checks if the object type is valid.
     *
     * @param objectType                 The object type to validate
     * @param constraintValidatorContext The constraint validator context
     * @return true if the object type is valid, false otherwise
     */
    @Override
    public boolean isValid(AbstractDTO<?> objectType, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(objectType) && Objects.nonNull(objectType.getId());
    }
}