package com.talentica.ecommerce.inventory.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SKURequestDTO {

    @NotBlank(message = "SKU code is required")
    @Size(max = 50, message = "SKU code must not exceed 50 characters")
    private String skuCode;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}