package com.fatih.redis_cache_spring_boot_demo.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(

        UUID id,
        String productName,
        String productDescription,
        Integer stockQuantity,
        BigDecimal price
) {
}
