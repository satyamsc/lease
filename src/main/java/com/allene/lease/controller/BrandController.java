package com.allene.lease.controller;

import com.allene.lease.dto.BrandDTO;

import java.util.List;

public interface BrandController {
    List<BrandDTO> list();

    BrandDTO save(BrandDTO brandDTO);

    BrandDTO findById(long id);

    BrandDTO update(BrandDTO brandDTO, long id);

    void delete(long id);
}
