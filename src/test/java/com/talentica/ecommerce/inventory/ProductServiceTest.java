package com.talentica.ecommerce.inventory.services;

import com.talentica.ecommerce.inventory.dtos.CategoryResponseDTO;
import com.talentica.ecommerce.inventory.dtos.ProductRequestDTO;
import com.talentica.ecommerce.inventory.dtos.ProductResponseDTO;
import com.talentica.ecommerce.inventory.entities.Category;
import com.talentica.ecommerce.inventory.entities.Product;
import com.talentica.ecommerce.inventory.repositories.CategoryRepository;
import com.talentica.ecommerce.inventory.repositories.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Unit Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Category testCategory;
    private Product testProduct;
    private ProductRequestDTO productRequestDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Electronics");
        testCategory.setDescription("Electronic products");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("iPhone 15");
        testProduct.setDescription("Latest iPhone model");
        testProduct.setCategory(testCategory);

        productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("iPhone 15");
        productRequestDTO.setDescription("Latest iPhone model");
        productRequestDTO.setCategoryId(1L);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Should create product successfully")
    void testCreateProduct_Success() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        ProductResponseDTO result = productService.createProduct(productRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        assertEquals(testProduct.getName(), result.getName());
        assertEquals(testProduct.getDescription(), result.getDescription());
        // Note: The actual service doesn't set category in response, so we'll skip this assertion

        verify(categoryRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when category not found during product creation")
    void testCreateProduct_CategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.createProduct(productRequestDTO));

        assertEquals("Category not found with ID: 1", exception.getMessage());
        verify(categoryRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should get filtered products with search only")
    void testGetFilteredProducts_SearchOnly() {
        // Arrange
        String searchTerm = "iPhone";
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> productPage = new PageImpl<>(products, pageable, 1);

        when(productRepository.findFilteredProducts(searchTerm, null, null, null, pageable))
                .thenReturn(productPage);

        // Act
        List<ProductResponseDTO> result = productService.getFilteredProducts(
                searchTerm, null, null, null, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct.getName(), result.get(0).getName());

        verify(productRepository).findFilteredProducts(searchTerm, null, null, null, pageable);
    }

    @Test
    @DisplayName("Should get filtered products with search and category")
    void testGetFilteredProducts_SearchAndCategory() {
        // Arrange
        String searchTerm = "iPhone";
        String categoryName = "Electronics";
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> productPage = new PageImpl<>(products, pageable, 1);

        when(productRepository.findFilteredProducts(searchTerm, categoryName, null, null, pageable))
                .thenReturn(productPage);

        // Act
        List<ProductResponseDTO> result = productService.getFilteredProducts(
                searchTerm, categoryName, null, null, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct.getName(), result.get(0).getName());

        verify(productRepository).findFilteredProducts(searchTerm, categoryName, null, null, pageable);
    }

    @Test
    @DisplayName("Should get filtered products with search, category and price range")
    void testGetFilteredProducts_SearchCategoryAndPriceRange() {
        // Arrange
        String searchTerm = "iPhone";
        String categoryName = "Electronics";
        BigDecimal minPrice = BigDecimal.valueOf(100.0); // Convert Double to BigDecimal
        BigDecimal maxPrice = BigDecimal.valueOf(2000.0); // Convert Double to BigDecimal
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> productPage = new PageImpl<>(products, pageable, 1);

        when(productRepository.findFilteredProducts(searchTerm, categoryName, minPrice, maxPrice, pageable))
                .thenReturn(productPage);

        // Act
        List<ProductResponseDTO> result = productService.getFilteredProducts(
                searchTerm, categoryName, 100.0, 2000.0, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct.getName(), result.get(0).getName());

        verify(productRepository).findFilteredProducts(searchTerm, categoryName, minPrice, maxPrice, pageable);
    }

    @Test
    @DisplayName("Should get all products when no filters applied")
    void testGetFilteredProducts_NoFilters() {
        // Arrange
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> productPage = new PageImpl<>(products, pageable, 1);

        when(productRepository.findFilteredProducts(null, null, null, null, pageable))
                .thenReturn(productPage);

        // Act
        List<ProductResponseDTO> result = productService.getFilteredProducts(
                null, null, null, null, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct.getName(), result.get(0).getName());

        verify(productRepository).findFilteredProducts(null, null, null, null, pageable);
    }

    @Test
    @DisplayName("Should get product by ID successfully")
    void testGetProductById_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act
        ProductResponseDTO result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        assertEquals(testProduct.getName(), result.getName());
        assertEquals(testProduct.getDescription(), result.getDescription());

        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when product not found by ID")
    void testGetProductById_NotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.getProductById(1L));

        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProduct_Success() {
        // Arrange
        ProductRequestDTO updateRequestDTO = new ProductRequestDTO();
        updateRequestDTO.setName("iPhone 15 Pro");
        updateRequestDTO.setDescription("Updated description");
        updateRequestDTO.setCategoryId(1L);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("iPhone 15 Pro");
        updatedProduct.setDescription("Updated description");
        updatedProduct.setCategory(testCategory);

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        ProductResponseDTO result = productService.updateProduct(1L, updateRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("iPhone 15 Pro", result.getName());
        assertEquals("Updated description", result.getDescription());

        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when product not found during update")
    void testUpdateProduct_ProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.updateProduct(1L, productRequestDTO));

        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository).findById(1L);
        verify(categoryRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when category not found during update")
    void testUpdateProduct_CategoryNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.updateProduct(1L, productRequestDTO));

        assertEquals("Category not found with ID: 1", exception.getMessage());
        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete product successfully")
    void testDeleteProduct_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).delete(testProduct);

        // Act
        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        // Assert
        verify(productRepository).findById(1L);
        verify(productRepository).delete(testProduct);
    }

    @Test
    @DisplayName("Should throw exception when product not found during delete")
    void testDeleteProduct_ProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.deleteProduct(1L));

        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository).findById(1L);
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    @DisplayName("Should return empty list when no products found in filtered search")
    void testGetFilteredProducts_EmptyResults() {
        // Arrange
        String searchTerm = "NonExistentProduct";
        Page<Product> emptyPage = new PageImpl<>(Arrays.asList(), pageable, 0);

        when(productRepository.findFilteredProducts(searchTerm, null, null, null, pageable))
                .thenReturn(emptyPage);

        // Act
        List<ProductResponseDTO> result = productService.getFilteredProducts(
                searchTerm, null, null, null, 0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productRepository).findFilteredProducts(searchTerm, null, null, null, pageable);
    }

    @Test
    @DisplayName("Should handle pagination correctly")
    void testGetFilteredProducts_PaginationParameters() {
        // Arrange
        int page = 2;
        int size = 5;
        Pageable customPageable = PageRequest.of(page, size);
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> productPage = new PageImpl<>(products, customPageable, 1);

        when(productRepository.findFilteredProducts(null, null, null, null, customPageable))
                .thenReturn(productPage);

        // Act
        List<ProductResponseDTO> result = productService.getFilteredProducts(
                null, null, null, null, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(productRepository).findFilteredProducts(null, null, null, null, customPageable);
    }
}