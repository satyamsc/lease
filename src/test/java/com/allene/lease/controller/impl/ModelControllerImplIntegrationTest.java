package com.allene.lease.controller.impl;

import com.allene.lease.dao.BrandRepository;
import com.allene.lease.dao.ContractRepository;
import com.allene.lease.dao.ModelRepository;
import com.allene.lease.dao.VehicleRepository;
import com.allene.lease.dto.ModelDTO;
import com.allene.lease.model.Brand;
import com.allene.lease.model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


class ModelControllerImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BrandRepository repository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ContractRepository contractRepository;

    private List<Model> models;

    @BeforeEach
    void setup() {
        contractRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        repository.deleteAll();
        insertModels();
    }

    private void insertModels() {
        Brand brandOne = new Brand();
        brandOne.setName("Brand 1");
        repository.save(brandOne);
        var build = List.of(Model.builder().name("Model 1").brand(brandOne).build(),
                Model.builder().name("Model 2").brand(brandOne).build()
        );
        models = modelRepository.saveAll(build);
    }

    @Test
    void testFindAllByBrandId() throws Exception {
        long brandId = models.get(0).getBrand().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/models")
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Model 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Model 2"));
    }

    @Test
    void testSaveModel() throws Exception {
        long brandId = models.get(0).getBrand().getId();

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setName("New Model");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicle/models")
                        .param("brandId", String.valueOf(brandId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(modelDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Model"));
    }
}
