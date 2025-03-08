package com.fatih.redis_cache_spring_boot_demo.controller;

import com.fatih.redis_cache_spring_boot_demo.controller.api.CategoryApi;
import com.fatih.redis_cache_spring_boot_demo.dto.request.CreateCategoryRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.request.UpdateCategoryRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.response.CategoryResponse;
import com.fatih.redis_cache_spring_boot_demo.entity.CategoryEntity;
import com.fatih.redis_cache_spring_boot_demo.manager.service.CategoryService;
import com.fatih.redis_cache_spring_boot_demo.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing categories.
 * Implements {@link CategoryApi} to handle category-related operations.
 */
@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    /**
     * Creates a new category.
     *
     * @param createCategoryRequest the category creation request
     * @return ResponseEntity containing the created category
     */
    @Override
    public ResponseEntity<CategoryResponse> createCategory(CreateCategoryRequest createCategoryRequest) {


        CategoryEntity requestedCategory = categoryMapper.toEntity(createCategoryRequest);

        CategoryEntity savedCategory = categoryService.createCategory(requestedCategory);

        CategoryResponse response = new CategoryResponse(savedCategory.getId(), savedCategory.getCategoryName(), savedCategory.getCategoryDescription());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates an existing category.
     *
     * @param updateCategoryRequest the category update request
     * @return ResponseEntity containing the updated category
     */
    @Override
    public ResponseEntity<CategoryResponse> updateCategory(UpdateCategoryRequest updateCategoryRequest) {

        CategoryEntity requestedCategory = categoryMapper.toEntity(updateCategoryRequest);

        CategoryEntity updatedCategory = categoryService.updateCategory(requestedCategory);

        CategoryResponse response = new CategoryResponse(updatedCategory.getId(), updatedCategory.getCategoryName(), updatedCategory.getCategoryDescription());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the category ID
     * @return ResponseEntity with HTTP status NO_CONTENT
     */
    @Override
    public ResponseEntity<HttpStatus> deleteCategory(UUID id) {

        categoryService.deleteCategory(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the category ID
     * @return ResponseEntity containing the found category
     */
    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(UUID id) {

        CategoryEntity foundCategory = categoryService.getCategoryById(id);

        CategoryResponse response = new CategoryResponse(foundCategory.getId(), foundCategory.getCategoryName(), foundCategory.getCategoryDescription());

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    /**
     * Retrieves all categories with pagination support.
     *
     * @param pageable pagination information
     * @return ResponseEntity containing a paginated list of categories
     */
    @Override
    public ResponseEntity<PageImpl<CategoryResponse>> getAllCategories(Pageable pageable) {
        Page<CategoryEntity> foundCategories = categoryService.getAllCategories(pageable);

        List<CategoryResponse> responses = foundCategories.getContent().stream()
                .map(categoryMapper::toResponse).toList();

        return new ResponseEntity<>(new PageImpl<>(responses, pageable, foundCategories.getTotalElements()), HttpStatus.OK);
    }
}
