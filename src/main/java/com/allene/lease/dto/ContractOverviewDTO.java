package com.allene.lease.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContractOverviewDTO {
    private Long contractNumber;
    private String customer;
    private String vehicle;
    private String vin;
    private BigDecimal monthlyRate;
    private BigDecimal vehiclePrice;
    private String linkToDetails;
}

