package com.allene.lease.controller.impl;

import com.allene.lease.controller.VehicleController;
import com.allene.lease.dto.VehicleDTO;
import com.allene.lease.service.VehicleService;
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
 * The VehicleControllerImpl class implements the VehicleController interface and provides RESTful endpoints for managing vehicles.
 */
@RequestMapping("/api/vehicles")
@RestController
@AllArgsConstructor
public class VehicleControllerImpl implements VehicleController {
    private final VehicleService vehicleService;

    /**
     * Saves a new vehicle.
     *
     * @param dto The VehicleDTO object containing the details of the vehicle to be saved.
     * @return The created VehicleDTO object.
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public VehicleDTO save(@RequestBody @Valid VehicleDTO dto) {
        return vehicleService.save(dto);
    }

    /**
     * Retrieves a vehicle by ID.
     *
     * @param id The ID of the vehicle.
     * @return The VehicleDTO object representing the vehicle.
     */
    @Override
    @Operation(summary = "Find vehicle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public VehicleDTO findById(@PathVariable("id") Long id) {
        return vehicleService.findById(id);
    }

    /**
     * Deletes a vehicle by ID.
     *
     * @param id The ID of the vehicle.
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete vehicle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehicle deleted", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        vehicleService.deleteById(id);
    }

    /**
     * Retrieves a list of all vehicles.
     *
     * @return A list of VehicleDTO objects representing the vehicles.
     */
    @Override
    @Operation(summary = "List all vehicles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles listed", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public List<VehicleDTO> list() {
        return vehicleService.findAll();
    }

    /**
     * Updates a vehicle by ID.
     *
     * @param dto The VehicleDTO object containing the updated details of the vehicle.
     * @param id  The ID of the vehicle.
     * @return The updated VehicleDTO object.
     */
    @Override
    @Operation(summary = "Update vehicle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public VehicleDTO update(@RequestBody @Valid VehicleDTO dto, @PathVariable("id") Long id) {
        return vehicleService.update(dto, id);
    }
}