package com.talentica.ecommerce.inventory;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.mockito.Mockito;

import com.talentica.ecommerce.inventory.repositories.CategoryRepository;
import com.talentica.ecommerce.inventory.repositories.ProductRepository;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    @Primary
    public CategoryRepository categoryRepository() {
        return Mockito.mock(CategoryRepository.class);
    }
}