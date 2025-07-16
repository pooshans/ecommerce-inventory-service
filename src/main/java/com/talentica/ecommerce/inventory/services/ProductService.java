package com.talentica.ecommerce.inventory.services;

import com.talentica.ecommerce.inventory.dtos.ProductRequestDTO;
import com.talentica.ecommerce.inventory.dtos.ProductResponseDTO;
import com.talentica.ecommerce.inventory.entities.Product;
import com.talentica.ecommerce.inventory.entities.Category;
import com.talentica.ecommerce.inventory.repositories.ProductRepository;
import com.talentica.ecommerce.inventory.repositories.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        logger.info("Creating product with name: {}", productRequestDTO.getName());
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", productRequestDTO.getCategoryId());
                    return new RuntimeException("Category not found with ID: " + productRequestDTO.getCategoryId());
                });

        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice()); // Set the price field
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        logger.debug("Product created successfully with ID: {}", savedProduct.getId());

        return mapToResponseDTO(savedProduct);
    }

    public ProductResponseDTO getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new RuntimeException("Product not found with ID: " + id);
                });
        logger.debug("Product fetched successfully: {}", product.getName());

        return mapToResponseDTO(product);
    }

    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new RuntimeException("Product not found with ID: " + id);
                });
        productRepository.delete(product);
        logger.debug("Product deleted successfully with ID: {}", id);
    }

    public List<ProductResponseDTO> getFilteredProducts(String search, String category, Double minPrice, Double maxPrice, int page, int size) {
        logger.info("Fetching filtered products with search: {}, category: {}, minPrice: {}, maxPrice: {}, page: {}, size: {}",
                search, category, minPrice, maxPrice, page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findFilteredProducts(
                search,
                category,
                minPrice != null ? BigDecimal.valueOf(minPrice) : null,
                maxPrice != null ? BigDecimal.valueOf(maxPrice) : null,
                pageable
        );

        logger.debug("Fetched {} products", productPage.getTotalElements());

        return productPage.getContent().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        logger.info("Updating product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new RuntimeException("Product not found with ID: " + id);
                });

        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", productRequestDTO.getCategoryId());
                    return new RuntimeException("Category not found with ID: " + productRequestDTO.getCategoryId());
                });

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        logger.debug("Product updated successfully with ID: {}", updatedProduct.getId());

        return mapToResponseDTO(updatedProduct);
    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        return responseDTO;
    }
}