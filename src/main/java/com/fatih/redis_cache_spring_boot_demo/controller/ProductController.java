package com.fatih.redis_cache_spring_boot_demo.controller;

import com.fatih.redis_cache_spring_boot_demo.controller.api.ProductApi;
import com.fatih.redis_cache_spring_boot_demo.dto.request.CreateProductRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.request.UpdateProductRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.response.ProductResponse;
import com.fatih.redis_cache_spring_boot_demo.entity.ProductEntity;
import com.fatih.redis_cache_spring_boot_demo.manager.service.ProductService;
import com.fatih.redis_cache_spring_boot_demo.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final ProductMapper productMapper;


    @Override
    public ResponseEntity<ProductResponse> createProduct(CreateProductRequest createProductRequest) {

        ProductEntity requestedProduct = productMapper.toEntity(createProductRequest);

        ProductEntity savedProduct = productService.createProduct(requestedProduct);

        ProductResponse response = productMapper.toResponse(savedProduct);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(UpdateProductRequest updateProductRequest) {

        ProductEntity requestedProduct = productMapper.toEntity(updateProductRequest);

        ProductEntity updatedProduct = productService.updateProduct(requestedProduct);

        ProductResponse response = productMapper.toResponse(updatedProduct);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(UUID id) {

        ProductEntity foundProduct = productService.getProductById(id);

        ProductResponse response = productMapper.toResponse(foundProduct);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteProduct(UUID id) {

        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PageImpl<ProductResponse>> getAllProducts(Pageable pageable) {

        List<ProductEntity> foundProducts = productService.getAllProducts(pageable);

        List<ProductResponse> responses = productMapper.toResponseList(foundProducts);

        return new ResponseEntity<>(new PageImpl<>(responses), HttpStatus.FOUND);
    }
}
