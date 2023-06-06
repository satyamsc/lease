package com.allene.lease.controller.impl;

import com.allene.lease.dao.BrandRepository;
import com.allene.lease.dao.ContractRepository;
import com.allene.lease.dao.ModelRepository;
import com.allene.lease.dao.VehicleRepository;
import com.allene.lease.dto.BrandDTO;
import com.allene.lease.model.Brand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

class BrandControllerImplIntegrationTest extends AbstractIntegrationTest {

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

    private List<Brand> brands;

    @BeforeEach
    void setup() {
        contractRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        repository.deleteAll();
        brands = insertBrands();
    }

    @Test
    void testListBrands() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/brands"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Brand 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Brand 2"));
    }

    private List<Brand> insertBrands() {
        Brand brandOne = new Brand();
        brandOne.setName("Brand 1");
        Brand brandTwo = new Brand();
        brandTwo.setName("Brand 2");
        return repository.saveAll(List.of(brandOne, brandTwo));
    }

    @Test
    void testSaveBrand() throws Exception {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("New Brand");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicle/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Brand"));
    }

    @Test
    void testFindBrandById() throws Exception {
        long brandId = brands.get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/brands/{id}", brandId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Brand 1"));
    }

    @Test
    void testUpdateBrand() throws Exception {
        long brandId = brands.get(0).getId();
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("Updated Brand");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/vehicle/brands/{id}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brandDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Brand"));
    }

    @Test
    void testDeleteBrand() throws Exception {
        long brandId = brands.get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/brands/{id}", brandId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
