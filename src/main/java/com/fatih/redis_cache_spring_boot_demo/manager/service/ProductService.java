package com.fatih.redis_cache_spring_boot_demo.manager.service;

import com.fatih.redis_cache_spring_boot_demo.entity.ProductEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {


    ProductEntity createProduct(ProductEntity requestedProduct);

    ProductEntity updateProduct(ProductEntity requestedProduct);

    ProductEntity getProductById(UUID id);

    void deleteProduct(UUID id);

    List<ProductEntity> getAllProducts(Pageable pageable);
}
