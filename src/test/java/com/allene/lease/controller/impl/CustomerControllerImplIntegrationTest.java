package com.allene.lease.controller.impl;

import com.allene.lease.dao.ContractRepository;
import com.allene.lease.dao.CustomerRepository;
import com.allene.lease.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

class CustomerControllerImplIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CustomerRepository repository;
    @Autowired
    ContractRepository contractRepository;
    Customer customer;

    @BeforeEach
     void setup() {
        contractRepository.deleteAll();
        repository.deleteAll();
        insertCustomer();
    }

    private void insertCustomer() {
        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDateOfBirth(LocalDate.of(2010, 10, 23));
        repository.save(customer);
    }

    @Test
    void testSaveCustomer() throws Exception {
        String jsonString = "{\"firstName\": \"John\", \"lastName\": \"Smith\", \"dateOfBirth\": \"22-10-2012\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("22-10-2012"));
    }

    @Test
    void testFindById() throws Exception {
        long customerId = customer.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}", customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("23-10-2010"));
    }

    @Test
    void testDelete() throws Exception {
        long customerId = customer.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/{id}", customerId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void testUpdate() throws Exception {
        long customerId = customer.getId();

        String jsonString = "{\"firstName\": \"Updated John\", \"lastName\": \"Smith\", \"dateOfBirth\": \"22-10-2012\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("22-10-2012"));
    }
}
