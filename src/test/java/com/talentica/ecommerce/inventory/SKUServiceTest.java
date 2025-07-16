package com.talentica.ecommerce.inventory;

import com.talentica.ecommerce.inventory.dtos.SKURequestDTO;
import com.talentica.ecommerce.inventory.dtos.SKUResponseDTO;
import com.talentica.ecommerce.inventory.entities.Product;
import com.talentica.ecommerce.inventory.entities.SKU;
import com.talentica.ecommerce.inventory.repositories.ProductRepository;
import com.talentica.ecommerce.inventory.repositories.SKURepository;
import com.talentica.ecommerce.inventory.services.SKUService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SKUServiceTest {

    @Mock
    private SKURepository skuRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SKUService skuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSKU_ValidProduct() {
        SKURequestDTO requestDTO = new SKURequestDTO();
        requestDTO.setSkuCode("SKU123");
        requestDTO.setDescription("Test SKU");
        requestDTO.setProductId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(skuRepository.existsByProductAndSkuCode(product, "SKU123")).thenReturn(false);

        SKU sku = new SKU();
        sku.setId(1L);
        sku.setSkuCode("SKU123");
        sku.setDescription("Test SKU");
        sku.setProduct(product);

        when(skuRepository.save(any(SKU.class))).thenReturn(sku);

        SKUResponseDTO responseDTO = skuService.createSKU(1L, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("SKU123", responseDTO.getSkuCode());
        verify(productRepository, times(1)).findById(1L);
        verify(skuRepository, times(1)).existsByProductAndSkuCode(product, "SKU123");
        verify(skuRepository, times(1)).save(any(SKU.class));
    }

    @Test
    void testCreateSKU_InvalidProduct() {
        SKURequestDTO requestDTO = new SKURequestDTO();
        requestDTO.setSkuCode("SKU123");
        requestDTO.setDescription("Test SKU");
        requestDTO.setProductId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> skuService.createSKU(1L, requestDTO));
        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(skuRepository, never()).existsByProductAndSkuCode(any(Product.class), anyString());
        verify(skuRepository, never()).save(any(SKU.class));
    }

    @Test
    void testUpdateSKU_ValidProductRelationship() {
        SKURequestDTO requestDTO = new SKURequestDTO();
        requestDTO.setSkuCode("SKU123");
        requestDTO.setDescription("Updated SKU");
        requestDTO.setProductId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        SKU sku = new SKU();
        sku.setId(1L);
        sku.setSkuCode("SKU123");
        sku.setProduct(product);

        when(skuRepository.findById(1L)).thenReturn(Optional.of(sku));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(skuRepository.save(any(SKU.class))).thenReturn(sku);

        SKUResponseDTO responseDTO = skuService.updateSKU(1L, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("SKU123", responseDTO.getSkuCode());
        assertEquals("Updated SKU", responseDTO.getDescription());
        verify(skuRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(skuRepository, times(1)).save(any(SKU.class));
    }

    @Test
    void testUpdateSKU_InvalidProductRelationship() {
        SKURequestDTO requestDTO = new SKURequestDTO();
        requestDTO.setSkuCode("SKU123");
        requestDTO.setDescription("Updated SKU");
        requestDTO.setProductId(2L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        SKU sku = new SKU();
        sku.setId(1L);
        sku.setSkuCode("SKU123");
        sku.setProduct(product);

        when(skuRepository.findById(1L)).thenReturn(Optional.of(sku));
        when(productRepository.findById(2L)).thenReturn(Optional.of(new Product()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> skuService.updateSKU(1L, requestDTO));
        assertEquals("SKU cannot be reassigned to a different product.", exception.getMessage());
        verify(skuRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(2L);
        verify(skuRepository, never()).save(any(SKU.class));
    }

    @Test
    void testDeleteSKU_Valid() {
        SKU sku = new SKU();
        sku.setId(1L);

        when(skuRepository.findById(1L)).thenReturn(Optional.of(sku));
        doNothing().when(skuRepository).delete(sku);

        skuService.deleteSKU(1L);

        verify(skuRepository, times(1)).findById(1L);
        verify(skuRepository, times(1)).delete(sku);
    }

    @Test
    void testDeleteSKU_NotFound() {
        when(skuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> skuService.deleteSKU(1L));
        assertEquals("SKU not found with ID: 1", exception.getMessage());
        verify(skuRepository, times(1)).findById(1L);
        verify(skuRepository, never()).delete(any(SKU.class));
    }
}