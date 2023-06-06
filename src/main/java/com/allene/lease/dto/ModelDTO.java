package com.allene.lease.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=false)
public class ModelDTO extends AbstractDTO<Long>{
    @NotBlank(message = "model name is mandatory")
    private String name;
    @JsonIgnore
    private BrandDTO brandDTO;
}
