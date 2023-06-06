package com.allene.lease.controller.impl;

import com.allene.lease.controller.BrandController;
import com.allene.lease.dto.BrandDTO;
import com.allene.lease.service.BrandService;
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
 * The BrandControllerImpl class implements the BrandController interface and provides RESTful endpoints for managing vehicle brands.
 */
@RequestMapping("/api/vehicle/brands")
@RestController
@AllArgsConstructor
public class BrandControllerImpl implements BrandController {
    private final BrandService brandService;

    /**
     * Retrieves a list of all vehicle brands.
     *
     * @return A list of BrandDTO objects representing the vehicle brands.
     */

    @Override
    @Operation(summary = "List all Vehicle Brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brands listed successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public List<BrandDTO> list() {
        return brandService.findAll();
    }

    /**
     * Creates a new vehicle brand.
     *
     * @param dto The BrandDTO object containing the details of the brand to be created.
     * @return The created BrandDTO object.
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Store Vehicle Brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public BrandDTO save(@RequestBody @Valid BrandDTO dto) {
        return brandService.save(dto);
    }

    /**
     * Retrieves a vehicle brand by its ID.
     *
     * @param id The ID of the brand to retrieve.
     * @return The BrandDTO object representing the retrieved brand.
     */
    @Override
    @Operation(summary = "Get Brand by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand is found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "404", description = "Brand is not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public BrandDTO findById(@PathVariable("id") long id) {
        return brandService.findById(id);
    }

    /**
     * Updates a vehicle brand.
     *
     * @param dto The BrandDTO object containing the updated details of the brand.
     * @param id  The ID of the brand to be updated.
     * @return The updated BrandDTO object.
     */
    @Override
    @Operation(summary = "Update Brand by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Brand is not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public BrandDTO update(@RequestBody @Valid BrandDTO dto, @PathVariable("id") long id) {
        return brandService.update(dto, id);
    }

    /**
     * Deletes a vehicle brand by its ID.
     *
     * @param id The ID of the brand to be deleted.
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Brand by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Brand is not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        brandService.deleteById(id);
    }
}
