package com.fatih.redis_cache_spring_boot_demo.controller.api;

import com.fatih.redis_cache_spring_boot_demo.dto.request.CreateCategoryRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.request.UpdateCategoryRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.response.CategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/category")
public interface CategoryApi {

    @PostMapping
    ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest);

    @PutMapping
    ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid UpdateCategoryRequest updateCategoryRequest);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCategory(@RequestParam @NotNull UUID id);

    @GetMapping("/id")
    ResponseEntity<CategoryResponse> getCategoryById(@RequestParam @NotNull UUID id);

    @GetMapping("/all")
    ResponseEntity<PageImpl<CategoryResponse>> getAllCategories(Pageable pageable);
}
