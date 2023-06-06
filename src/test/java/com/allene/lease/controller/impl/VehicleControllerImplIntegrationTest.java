package com.allene.lease.controller.impl;

import com.allene.lease.dao.BrandRepository;
import com.allene.lease.dao.ContractRepository;
import com.allene.lease.dao.ModelRepository;
import com.allene.lease.dao.VehicleRepository;
import com.allene.lease.dto.VehicleDTO;
import com.allene.lease.mapper.VehicleMapper;
import com.allene.lease.model.Brand;
import com.allene.lease.model.Model;
import com.allene.lease.model.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class VehicleControllerImplIntegrationTest extends AbstractIntegrationTest{
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

    @Autowired
    private VehicleMapper mapper;

    Vehicle vehicle;

    @BeforeEach
    void setUp() {
        contractRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        repository.deleteAll();
        insertVehicle();
    }

    private void insertVehicle() {
        Brand brandOne = new Brand();
        brandOne.setName("Brand 1");
        repository.save(brandOne);
        var build = List.of(Model.builder().name("Model 1").brand(brandOne).build(),
                Model.builder().name("Model 2").brand(brandOne).build()
        );
        List<Model> models = modelRepository.saveAll(build);
        vehicle = new Vehicle();
        vehicle.setBrand(brandOne);
        vehicle.setModel(models.get(0));
        vehicle.setPrice(BigDecimal.TEN);
        vehicle.setModelYear(2010);
        vehicle.setVin("1G8MG35X48Y106575");
        vehicleRepository.save(vehicle);

    }

    @Test
    void testSave() throws Exception {
        VehicleDTO vehicleDTO = mapper.asDTO(vehicle);
        VehicleDTO vehicleDTO1 = new VehicleDTO();
        vehicleDTO1.setModelYear(2011);
        vehicleDTO1.setPrice(BigDecimal.ONE);
        vehicleDTO1.setModel(vehicleDTO.getModel());
        vehicleDTO1.setBrand(vehicleDTO.getBrand());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vehicleDTO1)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand.name").value("Brand 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model.name").value("Model 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelYear").value(2011))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.ONE))
                .andDo(print());
    }

    @Test
    void testFindById() throws Exception {
        long id = vehicle.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand.name").value("Brand 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model.name ").value("Model 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin").value("1G8MG35X48Y106575"))
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        long id = vehicle.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vehicles/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void testList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand.name").value("Brand 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model.name").value("Model 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin").value("1G8MG35X48Y106575"))
                .andDo(print());
    }

    @Test
    void testUpdate() throws Exception {
        VehicleDTO vehicleDTO = mapper.asDTO(vehicle);
        vehicleDTO.setPrice(BigDecimal.ONE);
        vehicleDTO.setVin("JH4DB1650PS000680".trim());
        vehicleDTO.setModelYear(2000);
        long id = vehicle.getId();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/vehicles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vehicleDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand.name").value("Brand 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model.name").value("Model 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin").value("JH4DB1650PS000680"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelYear").value("2000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.ONE))
                .andDo(print());
    }
}
