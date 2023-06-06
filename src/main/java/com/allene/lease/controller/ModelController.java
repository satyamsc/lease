package com.allene.lease.controller;

import com.allene.lease.dto.ModelDTO;

import java.util.List;

public interface ModelController {
    List<ModelDTO> findAllByBrandId(long id);

    ModelDTO save(ModelDTO modelDTO, long id);

    public ModelDTO update(ModelDTO dto, long id);

    public void delete(long id);
}
