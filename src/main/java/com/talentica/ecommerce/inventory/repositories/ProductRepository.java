package com.talentica.ecommerce.inventory.repositories;

import com.talentica.ecommerce.inventory.entities.Product;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by name containing (case-insensitive search)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Find products by name and category name
    Page<Product> findByNameContainingIgnoreCaseAndCategoryName(String name, String categoryName, Pageable pageable);

    // Find products by name, category name, and price range
    Page<Product> findByNameContainingIgnoreCaseAndCategoryNameAndPriceBetween(
            String name, String categoryName, Double minPrice, Double maxPrice, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE "
            + "(LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR :search IS NULL) AND "
            + "(p.category.name = :category OR :category IS NULL) AND "
            + "(p.price >= :minPrice OR :minPrice IS NULL) AND "
            + "(p.price <= :maxPrice OR :maxPrice IS NULL)")
    Page<Product> findFilteredProducts(@Param("search") String search,
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);
}