package com.fatih.redis_cache_spring_boot_demo.manager.service;

import com.fatih.redis_cache_spring_boot_demo.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoryService {

    CategoryEntity createCategory(CategoryEntity requestedCategory);

    CategoryEntity updateCategory(CategoryEntity requestedCategory);

    void deleteCategory(UUID id);

    CategoryEntity getCategoryById(UUID id);

    Page<CategoryEntity> getAllCategories(Pageable pageable);
}
