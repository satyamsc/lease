package com.allene.lease.mapper;

import com.allene.lease.dto.BrandDTO;
import com.allene.lease.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper extends GenericMapper<Brand, BrandDTO> {
    @Override
    @Mapping(target = "id")
    Brand asEntity(BrandDTO dto);
}