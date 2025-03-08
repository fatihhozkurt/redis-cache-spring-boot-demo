package com.fatih.redis_cache_spring_boot_demo.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductRequest(

        @NotNull
        UUID id,

        String productName,

        String productDescription,

        Integer stockQuantity,

        BigDecimal price
) {
}
