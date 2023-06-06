package com.allene.lease.dto;

import com.allene.lease.validator.ValidateObject;
import com.allene.lease.validator.ValidateYear;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO extends AbstractDTO<Long> {
    @ValidateObject
    @NotNull(message = "brand is mandatory")
    private BrandDTO brand;
    @NotNull(message = "model is mandatory")
    @ValidateObject
    private ModelDTO model;
    @ValidateYear
    @NotNull(message = "modelYear is mandatory")
    private Integer modelYear;
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$", message = "vin must be a valid 17-character alphanumeric string")
    private String vin;
    @NotNull(message = "price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
    private BigDecimal price;

}