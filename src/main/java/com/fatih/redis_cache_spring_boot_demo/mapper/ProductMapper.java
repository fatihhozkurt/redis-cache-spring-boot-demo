package com.fatih.redis_cache_spring_boot_demo.mapper;

import com.fatih.redis_cache_spring_boot_demo.dto.request.CreateProductRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.request.UpdateProductRequest;
import com.fatih.redis_cache_spring_boot_demo.dto.response.ProductResponse;
import com.fatih.redis_cache_spring_boot_demo.entity.CategoryEntity;
import com.fatih.redis_cache_spring_boot_demo.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponse toResponse(ProductEntity productEntity) {
        return new ProductResponse(
                productEntity.getId(),
                productEntity.getProductName(),
                productEntity.getProductDescription(),
                productEntity.getStockQuantity(),
                productEntity.getPrice()
        );
    }

    public ProductEntity toEntity(CreateProductRequest createProductRequest) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(createProductRequest.categoryId());

        return ProductEntity.builder()
                .productName(createProductRequest.productName())
                .productDescription(createProductRequest.productDescription())
                .price(createProductRequest.price())
                .stockQuantity(createProductRequest.stockQuantity())
                .category(categoryEntity)
                .build();
    }

    public ProductEntity toEntity(UpdateProductRequest updateProductRequest) {
        return ProductEntity.builder()
                .id(updateProductRequest.id())
                .productName(updateProductRequest.productName())
                .productDescription(updateProductRequest.productDescription())
                .stockQuantity(updateProductRequest.stockQuantity())
                .price(updateProductRequest.price())
                .build();
    }

    public List<ProductResponse> toResponseList(List<ProductEntity> productEntities) {

        return productEntities.stream().map(this::toResponse).toList();
    }
}
