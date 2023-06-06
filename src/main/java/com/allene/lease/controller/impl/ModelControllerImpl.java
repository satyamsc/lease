package com.allene.lease.controller.impl;

import com.allene.lease.controller.ModelController;
import com.allene.lease.dto.ModelDTO;
import com.allene.lease.service.ModelService;
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
 * The ModelControllerImpl class implements the ModelController interface and provides RESTful endpoints for managing vehicle models.
 */
@RequestMapping("/api/vehicle/models")
@RestController
@AllArgsConstructor
public class ModelControllerImpl implements ModelController {
    private final ModelService service;

    /**
     * Retrieves a list of models by brand ID.
     *
     * @param brandId The ID of the brand.
     * @return A list of ModelDTO objects representing the models.
     */
    @Override
    @Operation(summary = "Find all models by brand ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Models found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public List<ModelDTO> findAllByBrandId(@RequestParam("brandId") long brandId) {
        return service.findAllByBrandId(brandId);
    }

    /**
     * Saves a new model for a brand.
     *
     * @param dto     The ModelDTO object containing the details of the model to be saved.
     * @param brandId The ID of the brand.
     * @return The created ModelDTO object.
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save model for a brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Model created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Model not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ModelDTO save(@RequestBody @Valid ModelDTO dto, @RequestParam("brandId") long brandId) {
        return service.save(dto, brandId);
    }
    /**
     * Updates a Model brand.
     *
     * @param dto The BrandDTO object containing the updated details of the brand.
     * @param id  The ID of the brand to be updated.
     * @return The updated BrandDTO object.
     */
    @Override
    @Operation(summary = "Update Model by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model updated successfully", content =
            @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Model is not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })

    @PutMapping("/{id}")
    public ModelDTO update(@RequestBody @Valid ModelDTO dto, @PathVariable("id") long id) {
        return service.update(dto, id);
    }

    /**
     * Deletes a vehicle brand by its ID.
     *
     * @param id The ID of the brand to be deleted.
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete model by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "model deleted successfully", content =
            @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "model is not found", content = @Content(mediaType =
                    "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.deleteById(id);
    }
}
