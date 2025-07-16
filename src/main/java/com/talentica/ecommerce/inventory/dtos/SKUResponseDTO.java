package com.talentica.ecommerce.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SKUResponseDTO {

    private Long id;
    private String skuCode;
    private String description;
    private ProductResponseDTO product;
}