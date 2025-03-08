package com.fatih.redis_cache_spring_boot_demo.repository;

import com.fatih.redis_cache_spring_boot_demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
