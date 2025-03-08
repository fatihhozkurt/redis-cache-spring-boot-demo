package com.fatih.redis_cache_spring_boot_demo.dao;

import com.fatih.redis_cache_spring_boot_demo.entity.ProductEntity;
import com.fatih.redis_cache_spring_boot_demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductDao {

    private final ProductRepository productRepository;

    public Page<ProductEntity> findAll(Pageable pageable) {

        return productRepository.findAll(pageable);
    }

    public void delete(ProductEntity foundProduct) {

        productRepository.delete(foundProduct);
    }

    public Optional<ProductEntity> findById(UUID id) {

        return productRepository.findById(id);
    }

    public ProductEntity save(ProductEntity productEntity) {

        return productRepository.save(productEntity);
    }
}
