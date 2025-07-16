package com.talentica.ecommerce.inventory.repositories;


import com.talentica.ecommerce.inventory.entities.Product;
import com.talentica.ecommerce.inventory.entities.SKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SKURepository extends JpaRepository<SKU, Long> {

    // Find SKUs by product
    List<SKU> findByProduct(Product product);

    // Find SKU by skuCode
    SKU findBySkuCode(String skuCode);

    // Check SKU existence for a product
    boolean existsByProductAndSkuCode(Product product, String skuCode);
}