package com.fatih.redis_cache_spring_boot_demo.controller.api;

import com.fatih.redis_cache_spring_boot_demo.dto.request.CreateProductRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.request.UpdateProductRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.response.ProductResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/product")
public interface ProductApi {

    @PostMapping
    ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest);

    @PutMapping
    ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid UpdateProductRequest updateProductRequest);

    @GetMapping("/id")
    ResponseEntity<ProductResponse> getProductById(@RequestParam @NotNull UUID id);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteProduct(@RequestParam @NotNull UUID id);

    @GetMapping("/all")
    ResponseEntity<PageImpl<ProductResponse>> getAllProducts(Pageable pageable);
}
