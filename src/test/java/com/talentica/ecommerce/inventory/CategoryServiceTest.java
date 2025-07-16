package com.talentica.ecommerce.inventory;

import com.talentica.ecommerce.inventory.dtos.CategoryRequestDTO;
import com.talentica.ecommerce.inventory.dtos.CategoryResponseDTO;
import com.talentica.ecommerce.inventory.entities.Category;
import com.talentica.ecommerce.inventory.exceptions.ResourceNotFoundException;
import com.talentica.ecommerce.inventory.repositories.CategoryRepository;
import com.talentica.ecommerce.inventory.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Electronics");
        requestDTO.setDescription("Category for electronic products");

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Category for electronic products");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.createCategory(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Electronics", responseDTO.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponseDTO responseDTO = categoryService.getCategoryById(1L);

        assertNotNull(responseDTO);
        assertEquals("Electronics", responseDTO.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateCategory() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Updated Electronics");
        requestDTO.setDescription("Updated description");

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO responseDTO = categoryService.updateCategory(1L, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Updated Electronics", responseDTO.getName());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_NotFound() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Updated Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(1L, requestDTO));
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCategory() {
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(1L));
        verify(categoryRepository, times(1)).findById(1L);
    }
}