package com.allene.lease.mapper;

import com.allene.lease.dto.ModelDTO;
import com.allene.lease.model.Model;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ModelMapper extends GenericMapper<Model, ModelDTO> {
    @Override
    @Mapping(target = "id")
    Model asEntity(ModelDTO dto);
}
