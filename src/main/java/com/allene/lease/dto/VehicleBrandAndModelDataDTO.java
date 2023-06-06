package com.allene.lease.dto;

import lombok.Data;

import java.util.List;

@Data
public class VehicleBrandAndModelDataDTO {
    private String brandName;
    private List<String> modelNames;
}