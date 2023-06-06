package com.allene.lease.controller.impl;

import com.allene.lease.controller.CustomerController;
import com.allene.lease.dto.CustomerDTO;
import com.allene.lease.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The CustomerControllerImpl class implements the CustomerController interface and provides RESTful endpoints for managing customers.
 */
@RequestMapping("/api/customers")
@RestController
@AllArgsConstructor
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    /**
     * Saves a new customer.
     *
     * @param dto The CustomerDTO object containing the details of the customer to be saved.
     * @return The created CustomerDTO object.
     */
    @Override
    @Operation(summary = "Save a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer saved successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO save(@RequestBody @Valid CustomerDTO dto) {
        return customerService.save(dto);
    }

    /**
     * Retrieves a customer by its ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The CustomerDTO object representing the retrieved customer.
     */
    @Override
    @Operation(summary = "Get customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public CustomerDTO findById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }

    /**
     * Deletes a customer by its ID.
     *
     * @param id The ID of the customer to be deleted.
     */
    @Override
    @Operation(summary = "Delete customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Customer not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        customerService.deleteById(id);
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return A list of CustomerDTO objects representing the customers.
     */
    @Override
    @Operation(summary = "List all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers listed successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public List<CustomerDTO> list() {
        return customerService.findAll();
    }

    /**
     * Updates a customer by its ID.
     *
     * @param dto The CustomerDTO object containing the updated customer details.
     * @param id  The ID of the customer to be updated.
     * @return The updated CustomerDTO object.
     */
    @Override
    @Operation(summary = "Update customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public CustomerDTO update(@RequestBody @Valid CustomerDTO dto, @PathVariable("id") Long id) {
        return customerService.update(dto, id);
    }
}