package com.allene.lease.mapper;

import com.allene.lease.dto.VehicleDTO;
import com.allene.lease.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface VehicleMapper extends GenericMapper<Vehicle, VehicleDTO> {
    @Override
    @Mapping(target = "id")
    Vehicle asEntity(VehicleDTO dto);
}