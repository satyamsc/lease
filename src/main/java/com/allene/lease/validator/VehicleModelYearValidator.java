package com.allene.lease.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

/**
 * Custom validator for validating the model year of a vehicle.
 */
public class VehicleModelYearValidator implements ConstraintValidator<ValidateYear, Integer> {
    /**
     * Validates the model year of a vehicle.
     *
     * @param year     The model year to validate
     * @param context  The constraint validator context
     * @return true if the model year is valid, false otherwise
     */
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) {
            // Null values are handled by other annotations like @NotNull or @NotBlank
            return true;
        }
        int currentYear = Year.now().getValue();
        // Validate the range of years
        return year >= 1900 && year <= currentYear;
    }
}
