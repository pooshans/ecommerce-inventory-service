package com.talentica.ecommerce.inventory.services;

import com.talentica.ecommerce.inventory.dtos.CategoryRequestDTO;
import com.talentica.ecommerce.inventory.dtos.CategoryResponseDTO;
import com.talentica.ecommerce.inventory.entities.Category;
import com.talentica.ecommerce.inventory.exceptions.ResourceNotFoundException;
import com.talentica.ecommerce.inventory.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        logger.info("Creating category with name: {}", categoryRequestDTO.getName());
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());

        Category savedCategory = categoryRepository.save(category);
        logger.debug("Category created successfully with ID: {}", savedCategory.getId());

        return mapToResponseDTO(savedCategory);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        logger.debug("Total categories fetched: {}", categories.size());

        return categories.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        return mapToResponseDTO(category);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return mapToResponseDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        categoryRepository.delete(category);
    }

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(category.getId());
        responseDTO.setName(category.getName());
        responseDTO.setDescription(category.getDescription());
        return responseDTO;
    }
}