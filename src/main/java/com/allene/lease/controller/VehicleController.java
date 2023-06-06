package com.allene.lease.controller;

import com.allene.lease.dto.VehicleDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface VehicleController {

    VehicleDTO save(VehicleDTO vehicle);

    VehicleDTO findById(Long id);

    void delete(Long id);

    List<VehicleDTO> list();

    VehicleDTO update(@RequestBody VehicleDTO dto, @PathVariable("id") Long id);
}