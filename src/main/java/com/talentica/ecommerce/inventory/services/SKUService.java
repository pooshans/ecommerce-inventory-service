package com.talentica.ecommerce.inventory.services;

import com.talentica.ecommerce.inventory.dtos.SKURequestDTO;
import com.talentica.ecommerce.inventory.dtos.SKUResponseDTO;
import com.talentica.ecommerce.inventory.entities.Product;
import com.talentica.ecommerce.inventory.entities.SKU;
import com.talentica.ecommerce.inventory.repositories.ProductRepository;
import com.talentica.ecommerce.inventory.repositories.SKURepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SKUService {

    private static final Logger logger = LoggerFactory.getLogger(SKUService.class);

    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private ProductRepository productRepository;

    public SKUResponseDTO createSKU(Long productId, SKURequestDTO skuRequestDTO) {
        logger.info("Creating SKU with code: {} for product ID: {}", skuRequestDTO.getSkuCode(), productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new RuntimeException("Product not found with ID: " + productId);
                });

        if (skuRepository.existsByProductAndSkuCode(product, skuRequestDTO.getSkuCode())) {
            logger.error("SKU with code {} already exists for product ID: {}", skuRequestDTO.getSkuCode(), productId);
            throw new RuntimeException("SKU with code " + skuRequestDTO.getSkuCode() + " already exists for the product.");
        }

        SKU sku = new SKU();
        sku.setSkuCode(skuRequestDTO.getSkuCode());
        sku.setDescription(skuRequestDTO.getDescription());
        sku.setProduct(product);

        SKU savedSKU = skuRepository.save(sku);
        logger.debug("SKU created successfully with ID: {}", savedSKU.getId());

        return mapToResponseDTO(savedSKU);
    }

    public SKUResponseDTO getSKUById(Long id) {
        logger.info("Fetching SKU with ID: {}", id);
        SKU sku = skuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("SKU not found with ID: {}", id);
                    return new RuntimeException("SKU not found with ID: " + id);
                });
        logger.debug("SKU fetched successfully: {}", sku.getSkuCode());

        return mapToResponseDTO(sku);
    }

    public void deleteSKU(Long id) {
        logger.info("Deleting SKU with ID: {}", id);
        SKU sku = skuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("SKU not found with ID: {}", id);
                    return new RuntimeException("SKU not found with ID: " + id);
                });
        skuRepository.delete(sku);
        logger.debug("SKU deleted successfully with ID: {}", id);
    }

    public SKUResponseDTO updateSKU(Long id, SKURequestDTO skuRequestDTO) {
        logger.info("Updating SKU with ID: {}", id);

        SKU sku = skuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("SKU not found with ID: {}", id);
                    return new RuntimeException("SKU not found with ID: " + id);
                });

        Product product = productRepository.findById(skuRequestDTO.getProductId())
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", skuRequestDTO.getProductId());
                    return new RuntimeException("Product not found with ID: " + skuRequestDTO.getProductId());
                });

        if (!sku.getProduct().getId().equals(product.getId())) {
            logger.error("SKU cannot be reassigned to a different product.");
            throw new RuntimeException("SKU cannot be reassigned to a different product.");
        }

        sku.setSkuCode(skuRequestDTO.getSkuCode());
        sku.setDescription(skuRequestDTO.getDescription());

        SKU updatedSKU = skuRepository.save(sku);
        logger.debug("SKU updated successfully with ID: {}", updatedSKU.getId());

        return mapToResponseDTO(updatedSKU);
    }

    public List<SKUResponseDTO> getSKUsByProductId(Long productId) {
        logger.info("Fetching SKUs for product ID: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new RuntimeException("Product not found with ID: " + productId);
                });

        List<SKU> skus = skuRepository.findByProduct(product);
        logger.debug("Fetched {} SKUs for product ID: {}", skus.size(), productId);

        return skus.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private SKUResponseDTO mapToResponseDTO(SKU sku) {
        SKUResponseDTO responseDTO = new SKUResponseDTO();
        responseDTO.setId(sku.getId());
        responseDTO.setSkuCode(sku.getSkuCode());
        responseDTO.setDescription(sku.getDescription());
        return responseDTO;
    }
}