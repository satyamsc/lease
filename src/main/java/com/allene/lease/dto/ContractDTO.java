package com.allene.lease.dto;

import com.allene.lease.validator.ValidateObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractDTO {
    private Long contractNumber;
    @NotNull(message = "MonthlyRate is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly rate must be greater than 0")
    private BigDecimal monthlyRate;
    @NotNull(message = "Customer is mandatory")
    @ValidateObject
    private CustomerDTO customer;
    @NotNull(message = "vehicle is mandatory")
    @ValidateObject
    private VehicleDTO vehicle;
}