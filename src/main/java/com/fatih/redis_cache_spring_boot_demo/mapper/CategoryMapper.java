package com.fatih.redis_cache_spring_boot_demo.mapper;

import com.fatih.redis_cache_spring_boot_demo.dto.request.CreateCategoryRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.request.UpdateCategoryRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.request.UpdateProductRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.response.CategoryResponse;
import com.fatih.redis_cache_spring_boot_demo.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(CategoryEntity categoryEntity) {

        return new CategoryResponse(
                categoryEntity.getId(),
                categoryEntity.getCategoryName(),
                categoryEntity.getCategoryDescription()
        );
    }

    public CategoryEntity toEntity(CreateCategoryRequest createCategoryRequest) {
        return CategoryEntity.builder()
                .categoryName(createCategoryRequest.categoryName())
                .categoryDescription(createCategoryRequest.categoryDescription())
                .build();
    }

    public CategoryEntity toEntity(UpdateCategoryRequest updateCategoryRequest) {
        return CategoryEntity.builder()
                .id(updateCategoryRequest.id())
                .categoryName(updateCategoryRequest.categoryName())
                .categoryDescription(updateCategoryRequest.categoryDescription())
                .build();
    }
}