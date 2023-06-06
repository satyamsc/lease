package com.allene.lease.service;

import com.allene.lease.dto.ModelDTO;

import java.util.List;

public interface ModelService extends GenericService<ModelDTO, Long> {

    ModelDTO save(ModelDTO dto, Long id);

    List<ModelDTO> saveAll(List<ModelDTO> dtos, Long id);

    List<ModelDTO> findAllByBrandId(Long id);
}