package com.talentica.ecommerce.inventory.repositories;


import com.talentica.ecommerce.inventory.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Custom query method to find a category by name
    Category findByName(String name);

    // Custom query method to check if a category exists by name
    boolean existsByName(String name);
}