package com.fatih.redis_cache_spring_boot_demo.dao;

import com.fatih.redis_cache_spring_boot_demo.entity.CategoryEntity;
import com.fatih.redis_cache_spring_boot_demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryDao {

    private final CategoryRepository categoryRepository;

    public CategoryEntity save(CategoryEntity requestedCategory) {

        return categoryRepository.save(requestedCategory);
    }

    public void delete(CategoryEntity categoryEntity) {

        categoryRepository.delete(categoryEntity);
    }

    public Page<CategoryEntity> findAll(Pageable pageable) {

        return categoryRepository.findAll(pageable);
    }

    public Optional<CategoryEntity> findByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public Optional<CategoryEntity> findById(UUID id) {

        return categoryRepository.findById(id);
    }
}
