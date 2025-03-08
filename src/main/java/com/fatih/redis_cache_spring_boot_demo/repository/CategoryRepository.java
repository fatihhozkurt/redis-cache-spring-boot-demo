package com.fatih.redis_cache_spring_boot_demo.repository;

import com.fatih.redis_cache_spring_boot_demo.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByCategoryName(String categoryName);
}
