package com.fatih.redis_cache_spring_boot_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "products")
public class ProductEntity extends BaseEntity implements Serializable {

    @Column(name = "product_name", nullable = false, length = 30)
    private String productName;

    @Column(name = "product_description", nullable = false, length = 500, columnDefinition = "TEXT")
    private String productDescription;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;


    @JsonBackReference("product_category")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
}
