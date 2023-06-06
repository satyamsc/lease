package com.allene.lease.controller.impl;

import com.allene.lease.dao.*;
import com.allene.lease.dto.ContractDTO;
import com.allene.lease.dto.ContractOverviewDTO;
import com.allene.lease.dto.CustomerDTO;
import com.allene.lease.dto.VehicleDTO;
import com.allene.lease.mapper.CustomerMapper;
import com.allene.lease.mapper.OverviewMapper;
import com.allene.lease.mapper.VehicleMapper;
import com.allene.lease.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContractControllerImplIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BrandRepository repository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OverviewMapper overviewMapper;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ObjectMapper objectMapper;
    private Vehicle vehicle;

    private Customer customer;

    private Contract contract;

    @BeforeEach
    void setUp() {
        contractRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        repository.deleteAll();
        customerRepository.deleteAll();
        insertVehicle();
    }

    @Test
    void testSaveContract() throws Exception {

        ContractDTO contractDTO = new ContractDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        // Set the properties of the contractDTO object
        contractDTO.setCustomer(customerDTO);
        contractDTO.setVehicle(vehicleDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(20000));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicle/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        ContractDTO savedContract = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), ContractDTO.class);

        assertThat(savedContract.getContractNumber()).isNotNull();
        assertThat(savedContract.getCustomer().getFirstName()).isEqualTo("John");
        assertThat(savedContract.getCustomer().getLastName()).isEqualTo("Doe");
        assertThat(savedContract.getVehicle().getModelYear()).isEqualTo(2010);
        assertThat(savedContract.getVehicle().getBrand().getName()).isEqualTo("Brand 1");
        assertThat(savedContract.getVehicle().getModel().getName()).isEqualTo("Model 1");

    }

    @Test
    void testFindContractByNumber() throws Exception {
        insertContract();
        Long contractNumber = contract.getContractNumber();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/contracts/{contractNumber}", contractNumber))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ContractDTO foundContract = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), ContractDTO.class);

        assertThat(foundContract.getContractNumber()).isEqualTo(contractNumber);
        assertThat(foundContract.getCustomer().getFirstName()).isEqualTo("John");
        assertThat(foundContract.getCustomer().getLastName()).isEqualTo("Doe");
        assertThat(foundContract.getMonthlyRate()).isEqualTo(new BigDecimal("123.00"));
        assertThat(foundContract.getVehicle().getBrand().getName()).isEqualTo("Brand 1");
        assertThat(foundContract.getVehicle().getVin()).isEqualTo("1G8MG35X48Y106575");
        assertThat(foundContract.getVehicle().getPrice()).isEqualTo(new BigDecimal("10.00"));
    }

    @Test
    void testDeleteContract() throws Exception {
        insertContract();
        Long contractId = contract.getContractNumber();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/contracts/{id}", contractId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testContractOverview() throws Exception {
        insertContract();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/contracts/overview"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ContractOverviewDTO> contractOverviews = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ContractOverviewDTO.class));

        assertThat(contractOverviews).isNotEmpty();

    }

    @Test
    void testUpdateContract() throws Exception {
        insertContract();
        ContractDTO dto = new ContractDTO();
        dto.setMonthlyRate(new BigDecimal(40000));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        dto.setCustomer(customerDTO);
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        dto.setVehicle(vehicleDTO);
        String jsonString = objectMapper.writeValueAsString(dto);
        Long contractNumber = contract.getContractNumber();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/vehicle/contracts/{contractNumber}",
                                contractNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ContractDTO updatedContract = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), ContractDTO.class);

        assertThat(updatedContract.getContractNumber()).isEqualTo(contractNumber);

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
        insertCustomer();
    }

    private void insertContract() {
        contract = new Contract();
        contract.setMonthlyRate(BigDecimal.valueOf(123L));
        contract.setVehicle(vehicle);
        contract.setCustomer(customer);
        contractRepository.save(contract);
    }

    private void insertCustomer() {
        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDateOfBirth(LocalDate.of(2010, 10, 23));
        customerRepository.save(customer);
    }
}
