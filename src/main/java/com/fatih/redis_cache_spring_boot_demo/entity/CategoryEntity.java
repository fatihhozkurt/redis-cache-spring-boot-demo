package com.fatih.redis_cache_spring_boot_demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@Table(name = "categories")
public class CategoryEntity extends BaseEntity implements Serializable {

    @Column(name = "category_name", nullable = false, length = 30, unique = true)
    private String categoryName;

    @Column(name = "category_description", nullable = false, length = 500, columnDefinition = "TEXT")
    private String categoryDescription;

    @JsonManagedReference("product_category")
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductEntity> products;
}
