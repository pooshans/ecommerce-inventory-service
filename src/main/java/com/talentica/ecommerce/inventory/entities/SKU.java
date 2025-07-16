package com.talentica.ecommerce.inventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "skus", uniqueConstraints = {
        @UniqueConstraint(columnNames = "skuCode")
})
@Data
public class SKU {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "SKU Code is required")
    private String skuCode;

    @Column(nullable = false)
    @Positive(message = "Price must be positive")
    private Double price;

    @Column(nullable = false)
    @PositiveOrZero(message = "Stock quantity must be non-negative")
    private Integer stockQuantity;

    @Column(columnDefinition = "json")
    private String attributes;

    @Column(length = 255)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}