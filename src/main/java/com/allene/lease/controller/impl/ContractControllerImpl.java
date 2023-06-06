package com.allene.lease.controller.impl;

import com.allene.lease.controller.ContractController;
import com.allene.lease.dto.ContractDTO;
import com.allene.lease.dto.ContractOverviewDTO;
import com.allene.lease.mapper.OverviewMapper;
import com.allene.lease.service.ContractService;
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
 * The ContractControllerImpl class implements the ContractController interface and provides RESTful endpoints for managing contracts.
 */
@RequestMapping("/api/vehicle/contracts")
@RestController
@AllArgsConstructor
public class ContractControllerImpl implements ContractController {
    private final ContractService contractService;
    private final OverviewMapper mapper;

    /**
     * Creates a new license contract.
     *
     * @param dto The ContractDTO object containing the details of the contract to be created.
     * @return The created ContractDTO object.
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Store License Contract.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contract Created", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ContractDTO save(@RequestBody @Valid ContractDTO dto) {
        return contractService.save(dto);
    }

    /**
     * Retrieves a contract by its contract number.
     *
     * @param number The contract number.
     * @return The ContractDTO object representing the retrieved contract.
     */
    @Override
    @Operation(summary = "Get Contract by Contract Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "400", description = "Contract is not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{contractNumber}")
    public ContractDTO findByContract(@PathVariable("contractNumber") Long number) {
        return contractService.findByContractNum(number);
    }

    /**
     * Deletes a contract by its ID.
     *
     * @param id The ID of the contract to be deleted.
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Contract by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contract deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Contract is not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        contractService.deleteById(id);
    }

    /**
     * Retrieves an overview of all contracts.
     *
     * @return A list of ContractOverviewDTO objects representing the contract overview.
     */
    @Override
    @Operation(summary = "List all Contracts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contracts listed successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")),

    })

    @GetMapping("/overview")
    public List<ContractOverviewDTO> overview() {
        return mapper.mapToOverviewDTOs(contractService.findAll());
    }

    /**
     * Updates a contract by its contract number.
     *
     * @param dto        The ContractDTO object containing the updated contract details.
     * @param contractNo The contract number.
     * @return The updated ContractDTO object.
     */
    @Override
    @Operation(summary = "Update Contract by Contract Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract updated successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Contract is not found ", content =
            @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{contractNumber}")
    public ContractDTO update(@RequestBody @Valid ContractDTO dto,
                              @PathVariable("contractNumber") Long contractNo) {
        return contractService.update(dto, contractNo);
    }
}