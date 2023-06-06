package com.allene.lease.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=false)
public class BrandDTO extends AbstractDTO<Long>{
    @NotBlank(message = "Brand name is mandatory")
    private String name;
}
