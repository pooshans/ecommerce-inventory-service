package com.talentica.ecommerce.inventory.controllers;

import com.talentica.ecommerce.inventory.dtos.SKURequestDTO;
import com.talentica.ecommerce.inventory.dtos.SKUResponseDTO;
import com.talentica.ecommerce.inventory.services.SKUService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "SKU Management", description = "Endpoints for managing SKUs")
public class SKUController {

    @Autowired
    private SKUService skuService;

    @Operation(summary = "Create a new SKU for a product", description = "Creates a new SKU associated with a specific product")
    @PostMapping("/products/{productId}/skus")
    public ResponseEntity<SKUResponseDTO> createSKU(
            @PathVariable Long productId,
            @Valid @RequestBody SKURequestDTO skuRequestDTO) {
        SKUResponseDTO createdSKU = skuService.createSKU(productId, skuRequestDTO);
        return new ResponseEntity<>(createdSKU, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all SKUs for a product", description = "Retrieves all SKUs associated with a specific product")
    @GetMapping("/products/{productId}/skus")
    public ResponseEntity<List<SKUResponseDTO>> getSKUsByProductId(@PathVariable Long productId) {
        List<SKUResponseDTO> skus = skuService.getSKUsByProductId(productId);
        return new ResponseEntity<>(skus, HttpStatus.OK);
    }

    @Operation(summary = "Get SKU by ID", description = "Fetches a SKU by its unique ID")
    @GetMapping("/skus/{id}")
    public ResponseEntity<SKUResponseDTO> getSKUById(@PathVariable Long id) {
        SKUResponseDTO sku = skuService.getSKUById(id);
        return new ResponseEntity<>(sku, HttpStatus.OK);
    }

    @Operation(summary = "Update SKU by ID", description = "Updates the details of a SKU by its unique ID")
    @PutMapping("/skus/{id}")
    public ResponseEntity<SKUResponseDTO> updateSKU(
            @PathVariable Long id,
            @Valid @RequestBody SKURequestDTO skuRequestDTO) {
        SKUResponseDTO updatedSKU = skuService.updateSKU(id, skuRequestDTO);
        return new ResponseEntity<>(updatedSKU, HttpStatus.OK);
    }

    @Operation(summary = "Delete SKU by ID", description = "Deletes a SKU by its unique ID")
    @DeleteMapping("/skus/{id}")
    public ResponseEntity<Void> deleteSKU(@PathVariable Long id) {
        skuService.deleteSKU(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}